package org.fxapps.repasse;

import java.lang.reflect.Type;
import java.net.URL;
import java.net.URLConnection;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;

import org.fxapps.drawingfx.DrawingApp;
import org.fxapps.repasse.model.Agregacao;
import org.fxapps.repasse.model.Municipio;
import org.fxapps.repasse.model.TipoAgregacao;

import javafx.collections.FXCollections;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.effect.BlendMode;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;

public class RepasseApp extends DrawingApp {

	@SuppressWarnings("serial")
	Type MUNICIPIO_LIST_TYPE = new ArrayList<Municipio>() {
	}.getClass().getGenericSuperclass();

	final String[] STATES = { "AC", "AL", "AP", "AM", "BA", "CE", "DF", "ES", "GO", "MA", "MT", "MS", "MG", "PA", "PB",
			"PR", "PE", "PI", "RJ", "RN", "RS", "RO", "RR", "SC", "SP", "SE", "TO" };

	final String[] YEARS = { "2011", "2012", "2013", "2014", "2015", "2016" };
//	final String[] YEARS = { "2011", "2012" };


	final String CITIES_URL = "http://repasse.ufabc.edu.br/rest/estado/%s/municipios";
	final String PER_CAPITA_AGGREGATION_URL = "http://repasse.ufabc.edu.br/rest/agregacao/percapita/%s/%s/municipio/%s";

	static private NumberFormat nfPtBr = NumberFormat.getNumberInstance(new Locale("pt", "BR"));

	private ComboBox<String> cmbStates;
	private ComboBox<Municipio> cmbCities;
	private ComboBox<TipoAgregacao> cmbAggregation;
	private ToggleButton tgPause;
	private CheckBox chkShowEvolution;
	Jsonb jsonb = JsonbBuilder.create();


	// this is used when seeing all years
	private List<ValueBallGroup> valueBallGroups;

	// this is used when checking the transfer evolution
	private ArrayList<Agregacao> allAggregations;
	private Map<String, ValueBall> valueBallForItem;
	private List<ValueBall> currentValueBalls;
	private int currentAggreationIndex = 0;

	private double currentTotal;
	private int DEFAULT_TIME_TO_WAIT = 100;
	private int timeToWait = DEFAULT_TIME_TO_WAIT;



	public static void main(String[] args) {
		launch();
	}

	public void setup() {
		canvas.setBlendMode(BlendMode.MULTIPLY);
		nfPtBr.setMaximumFractionDigits(3);
		title = "Repasses para municípios";
		width = 1200;
		height = 800;
		createControls();
		frames = 30;
		ctx.setTextAlign(TextAlignment.CENTER);
		initializeDataVariables();
	}

	private void initializeDataVariables() {
		valueBallGroups = new  ArrayList<>();
		allAggregations = new ArrayList<>();
		valueBallForItem = new HashMap<>();
		currentValueBalls = new ArrayList<>();
		currentAggreationIndex = 0;
		
	}

	public void draw() {
		ctx.setFill(Color.AZURE);
		ctx.fillRect(0, 0, width, height);
		if (valueBallGroups == null  || valueBallGroups.size() == 0) {
			ctx.setFill(Color.DARKBLUE);
			ctx.setFont(Font.font(40));
			ctx.fillText("Selecione valores abaixo para começar!", width / 2, height / 2);
		} 
		
		if(chkShowEvolution.isSelected() && allAggregations.size() > 0) {
			currentValueBalls.forEach(v -> {
				v.show();
				v.update();
			});
			// draw the larger first
			ctx.setFont(Font.font(null, FontWeight.EXTRA_BOLD, FontPosture.REGULAR, 25));
			ctx.setFill(Color.DARKBLUE);
			ctx.fillText("Repasses (per capita) em " + allAggregations.get(currentAggreationIndex).getAno() + " foram de " + toBrMoney(currentTotal), width / 2, 40);
			int yearSpot = width / (allAggregations.size());
			for (Agregacao a : allAggregations) {
				int i = allAggregations.indexOf(a);
				String txt = String.valueOf(a.getAno());
				if(a.getAno() == allAggregations.get(currentAggreationIndex).getAno()) {
					ctx.setFont(Font.font(null, FontWeight.EXTRA_BOLD, FontPosture.ITALIC, 60));
				} else {
					ctx.setFont(Font.font(null, FontWeight.NORMAL, FontPosture.ITALIC, 40));
				}
				ctx.setFill(Color.DARKBLUE);
				ctx.setTextAlign(TextAlignment.CENTER);
				ctx.fillText(txt, i * yearSpot  + yearSpot / 2  , height - 30);
			}
			
			boolean allGrownUP = valueBallForItem.values().stream().map(v -> v.getR() == v.getNewR()).reduce((b, b2) -> b && b2).get();
			if(allGrownUP && timeToWait-- < 0 && !tgPause.isSelected()) {
				timeToWait = DEFAULT_TIME_TO_WAIT;
				currentAggreationIndex++;
				if(currentAggreationIndex >= allAggregations.size()) {
					currentAggreationIndex = 0;
				}
				loadCurrentAggregation();
			}
			
		} else {
			for (ValueBallGroup vbg : valueBallGroups) {
				vbg.update();
				vbg.show();
				ctx.setFill(Color.LIGHTBLUE.brighter());
				ctx.strokeLine(vbg.getX(), vbg.getY(), vbg.getX() , vbg.getY() + vbg.getHeight());
			}
		}
	}

	private void createControls() {
		cmbStates = new ComboBox<String>(FXCollections.observableArrayList(STATES));
		cmbAggregation = new ComboBox<>(FXCollections.observableArrayList(TipoAgregacao.values()));
		tgPause = new ToggleButton("Pausar no ano atual");
		cmbCities = new ComboBox<>();
		chkShowEvolution = new CheckBox("Evolução dos Repasses");
		chkShowEvolution.setSelected(true);
		Button btnLoad = new Button("Carregar");

		HBox hbControls = new HBox(20,  new Label("Estado"), cmbStates,
				new Label("Município"), cmbCities, new Label("Mostrar por"), cmbAggregation, btnLoad, chkShowEvolution, tgPause);
		hbControls.setAlignment(Pos.CENTER);

		cmbCities.setMaxWidth(150);
		cmbCities.disableProperty().bind(cmbStates.getSelectionModel().selectedItemProperty().isNull());
		btnLoad.disableProperty().bind(cmbCities.getSelectionModel().selectedItemProperty().isNull());
		cmbStates.getSelectionModel().selectedItemProperty().addListener(l -> loadCities());

		hbControls.setAlignment(Pos.CENTER);
		setBottom(hbControls);

		cmbStates.getSelectionModel().select(0);
		cmbAggregation.getSelectionModel().select(0);
		btnLoad.setOnAction(e -> fetchData());
	}

	private void loadCities() {
		String state = cmbStates.getSelectionModel().getSelectedItem();
		String citiesUrl = String.format(CITIES_URL, state);
		try {
			URLConnection conn = new URL(citiesUrl).openConnection();
			ArrayList<Municipio> cityList = jsonb.fromJson(conn.getInputStream(), MUNICIPIO_LIST_TYPE);
			cmbCities.getItems().setAll(cityList);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void fetchData() {
		initializeDataVariables();
		String id = String.valueOf(cmbCities.getSelectionModel().getSelectedItem().getId());
		TipoAgregacao a = cmbAggregation.getSelectionModel().getSelectedItem();
		try {
			for(String year : YEARS) {
				String aggrUrl = String.format(PER_CAPITA_AGGREGATION_URL, a.getChave(), year, id);
				URLConnection conn = new URL(aggrUrl).openConnection();
				Agregacao aggregation = jsonb.fromJson(conn.getInputStream(), Agregacao.class);
				addAggregation(aggregation);
			}
			loadCurrentAggregation();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void addAggregation(Agregacao aggregation) {
		int groupSize = width / YEARS.length;
		int x = valueBallGroups.size() * groupSize;
		String year = String.valueOf(aggregation.getAno());
		ValueBallGroup vbg = new ValueBallGroup(year , Color.LIGHTBLUE, Color.DARKBLUE, x, 0, groupSize, height, aggregation.getDadosAgregados(), this::toBrMoney, this);
		allAggregations.add(aggregation);
		valueBallGroups.add(vbg);
	}
	
	private void loadCurrentAggregation() {
		Agregacao currentAggregation = allAggregations.get(currentAggreationIndex);
		currentTotal = currentAggregation.getDadosAgregados().values().stream().mapToDouble(v -> v).sum();
		// we must make sure that all values has their own ValueBall
		currentAggregation.getDadosAgregados().forEach((k, v) -> {
			Color[] colors = ValueBallGroup.colorsForData(k.toString());
			valueBallForItem.computeIfAbsent(k.toString(), key -> 
			new ValueBall(0, height / 2, 0, colors[0], colors[1], k.toString(), toBrMoney(v), this));
		});
		// now we must again kill the balls that are not in this current aggregation
		valueBallForItem.entrySet().stream().filter(e -> currentAggregation.getDadosAgregados().get(e.getKey().toString()) == null
				).forEach(e -> { 
			e.getValue().setNewR(0); 
			e.getValue().setValue(null);
		});
		
		// finally we set the corresponding ball value and this will trigger the ball size adjustment
		currentAggregation.getDadosAgregados().forEach((k, v) -> {
			int r = (int) map(v, 0, currentTotal, 100, 600);
			ValueBall valueBall = valueBallForItem.get(k.toString());
			valueBall.setNewR(r);
			valueBall.setValue(toBrMoney(v));
			valueBall.setStartBoundary(valueBall.getStartBoundary().add(r + 50, r + 100));
			valueBall.setEndBoundary(valueBall.getEndBoundary().subtract(r + 50, r + 100));
		});
		currentValueBalls = valueBallForItem.values().stream().collect(Collectors.toList());
		Collections.sort(currentValueBalls, (a, b) -> {
			return b.getNewR() - a.getNewR();
		});
		// on first index we will not grow up and we will set the position for all the value balls
		if(currentAggreationIndex == 0) {
			int nextX = width;
			for (ValueBall vb : currentValueBalls) {
				int maxR = valueBallForItem.values().stream().filter(v -> v.getLabel().equals(vb.getLabel())).map(ValueBall::getNewR).max(Integer::compare).get();
				nextX =  nextX - maxR -10;
				int randomYIncrement = 0;
				if(nextX <  0) {
					nextX = maxR;
					randomYIncrement = 100;
				}
				vb.setX(nextX);
				int randomY = random.nextInt(50 + randomYIncrement);
				randomY*= random.nextInt(2) == 1 ? -1: 1;
				vb.setY(height / 2 - maxR / 2 + randomY);
			}
			for (ValueBall vb : currentValueBalls) {
				vb.setR(vb.getNewR());
			}
		}

	}
	
	@Override
	public void mouseMoved() {
		for (ValueBall vb: currentValueBalls) {
			if(vb.isSelected()) {
				if(vb.getX() < width && vb.getX() > 0);
					vb.setX((int)mouseX - vb.getNewR() / 2);
				if(vb.getY() < height && vb.getY() > 0);
					vb.setY((int)mouseY - vb.getNewR() / 2);
			}
		}
	}
	
	@Override
	public void mouseCliked() {
		for (ValueBall vb: currentValueBalls) {
			if(vb.isSelected()) {
				vb.setSelected(false);
				return;
			}
		}
		ValueBall lastClickedBall = null;
		for (ValueBall vb: currentValueBalls) {
			if(vb.intersect(mouseX, mouseY)) {
				lastClickedBall = vb;
			}
		}
		if(lastClickedBall != null) {
			lastClickedBall.setSelected(true);
		}
		
	}

	private String toBrMoney(Double value) {
		return "R$ " + nfPtBr.format(value);
	}

}
