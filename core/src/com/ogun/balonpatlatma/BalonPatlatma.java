package com.ogun.balonpatlatma;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

import java.util.Iterator;

public class BalonPatlatma implements ApplicationListener {

	/////////////////////////////////////////////////GEREKLİ DEĞİŞKENLER//////////////////////////////////////////////////
	protected OrthographicCamera kamera;
	private SpriteBatch batch;

	private Texture rsmkirmiziBalon;
	private Texture rsmsariBalon;
	private Texture rsmsiyahBalon;
	private Texture rsmyesilBalon;

	private Texture rsmMenuArkaplan;
	private Texture rsmSeviye1Arkaplan;
	private Texture rsmSeviye2Arkaplan;
	private Texture rsmSeviye3Arkaplan;
	private Texture rsmBasariliArkaplan;
	private Texture rsmBasarisizArkaplan;
	private Texture rsmHakkindaArkaplan;

	private Texture rsmOynaButonu;
	private Texture rsmSesiAcButonu;
	private Texture rsmSesiKapatButonu;
	private Texture rsmHakkindaButonu;
	private Texture rsmCikisButonu;
	private Texture rsmMenuButonu;

	private Texture rsmGru;
	private Texture rsmMuzcuMinyon;
	private Texture rsmMuzcuMinyon2;
	private Texture rsmMuz;

	private Sprite sprite;

	private Array<Rectangle> kirmiziBalonlar;
	private Array<Rectangle> sariBalonlar;
	private Array<Rectangle> siyahBalonlar;
	private Array<Rectangle> yesilBalonlar;

	private Rectangle dokunma;
	private Rectangle muz;

	private Rectangle oynaButonu;
	private Rectangle sesButonu;
	private Rectangle hakkindaButonu;
	private Rectangle cikisButonu;
	private Rectangle menuButonu;

	private BitmapFont font;

	private long sonBalonZamani;
	private long sariBalonZamani;
	private long zamanlayici;
	private long sesZamani;
	private int kirmiziYon;
	private int siyahRenkDegis = 1;
	private int yesilRenkDegis = 1;
	private int sesKontrol = 1;
	private int skor = 0;
	private int toplamskor = 0;
	private int rekor;
	private int sure = 30;
	private int ekran = 0;
	private int balonsil = 700;
	private float volume = 1.0f;
	private int kirmizipatladi = 0;
	private int saripatladi = 0;
	private int yesilpatladi = 0;
	private int siyahpatladi = 0;
	private int muzdonme=0;

	private Sound kyBalonSes;
	private Sound sariBalonSes;
	private Sound siyahBalonSes;
	private Music bananaSes;
	private Music dusmeSes;
	private Music gruSes;
	private Music menuSes;
	private Music oyunSes;

	private static Preferences score;

	public BalonPatlatma() {
	}


	/////////////////////////////////////////////////KIRMIZI BALON URETİR///////////////////////////////////////////////
	private void kirmiziBalonUret() {

		Rectangle kirmiziBalon = new Rectangle();
		kirmiziBalon.width = 200;
		kirmiziBalon.height = 200;
		kirmiziBalon.x = MathUtils.random(0, 1920 - 200);
		kirmiziBalon.y = MathUtils.random(0, 300);

		kirmiziBalonlar.add(kirmiziBalon);
		sonBalonZamani = TimeUtils.nanoTime();
	}

	/////////////////////////////////////////////////SARI BALON URETİR///////////////////////////////////////////////////
	private void sariBalonUret() {

		Rectangle sariBalon = new Rectangle();
		sariBalon.width = 200;
		sariBalon.height = 200;
		sariBalon.x = MathUtils.random(0, 1920 - 200);
		sariBalon.y = MathUtils.random(0, balonsil);

		sariBalonlar.add(sariBalon);
		sariBalonZamani = TimeUtils.nanoTime();
	}

	/////////////////////////////////////////////////SİYAH BALON URETİR///////////////////////////////////////////////////
	private void siyahBalonUret() {

		Rectangle siyahBalon = new Rectangle();
		siyahBalon.width = 200;
		siyahBalon.height = 200;
		siyahBalon.x = MathUtils.random(0, 1920 - 200);
		siyahBalon.y = -200;

		siyahBalonlar.add(siyahBalon);
	}

	/////////////////////////////////////////////////YEŞİL BARON ÜRETİR///////////////////////////////////////////////////
	private void yesilBalonUret() {

		Rectangle yesilBalon = new Rectangle();
		yesilBalon.width = 200;
		yesilBalon.height = 200;
		yesilBalon.x = MathUtils.random(0, 1920 - 200);
		yesilBalon.y = -200;

		yesilBalonlar.add(yesilBalon);
	}

	//oyun ilk çalıştığında gerekli resimleri müzikleri oluşturur.
	//1 defaya mahsus çalışır.
	@Override
	public void create() {

		score = Gdx.app.getPreferences("HighScore");

		//kamera çağırma
		kamera = new OrthographicCamera();
		kamera.setToOrtho(false,1920,1080);

		//resimleri çağırma
		rsmkirmiziBalon = new Texture(Gdx.files.internal("balonlar/kirmizi.png"));
		rsmsariBalon = new Texture(Gdx.files.internal("balonlar/sari.png"));
		rsmsiyahBalon = new Texture(Gdx.files.internal("balonlar/siyah.png"));
		rsmyesilBalon = new Texture(Gdx.files.internal("balonlar/yesil.png"));

		rsmMenuArkaplan = new Texture("arkaplanlar/menuarkaplan.jpg");
		rsmSeviye1Arkaplan = new Texture("arkaplanlar/seviye1arkaplan.jpg");
		rsmSeviye2Arkaplan = new Texture("arkaplanlar/seviye2arkaplan.jpg");
		rsmSeviye3Arkaplan = new Texture("arkaplanlar/seviye3arkaplan.jpg");
		rsmBasariliArkaplan = new Texture("arkaplanlar/basariliarkaplan.jpg");
		rsmBasarisizArkaplan = new Texture("arkaplanlar/basarisizarkaplan.jpg");
		rsmHakkindaArkaplan = new Texture("arkaplanlar/hakkindaarkaplan.jpg");

		rsmOynaButonu = new Texture(Gdx.files.internal("butonlar/oynabutonu.png"));
		rsmSesiAcButonu = new Texture(Gdx.files.internal("butonlar/sesiacbutonu.png"));
		rsmSesiKapatButonu = new Texture(Gdx.files.internal("butonlar/sesikapatbutonu.png"));
		rsmHakkindaButonu = new Texture(Gdx.files.internal("butonlar/hakkindabutonu.png"));
		rsmCikisButonu = new Texture(Gdx.files.internal("butonlar/cikisbutonu.png"));
		rsmMenuButonu = new Texture(Gdx.files.internal("butonlar/menubutonu.png"));

		rsmGru = new Texture(Gdx.files.internal("karakterler/gru.png"));
		rsmMuzcuMinyon = new Texture(Gdx.files.internal("karakterler/muzcuminyon.png"));
		rsmMuzcuMinyon2 = new Texture(Gdx.files.internal("karakterler/muzcuminyon2.png"));
		rsmMuz = new Texture(Gdx.files.internal("karakterler/muz.png"));

		sprite = new Sprite(rsmMuz);

		kirmiziBalonlar = new Array<Rectangle>();
		sariBalonlar = new Array<Rectangle>();
		siyahBalonlar = new Array<Rectangle>();
		yesilBalonlar = new Array<Rectangle>();

		//SpriteBatch
		batch = new SpriteBatch();

		//Ses Dosyaları
		kyBalonSes = Gdx.audio.newSound(Gdx.files.internal("sesler/kybalonses.mp3"));
		sariBalonSes = Gdx.audio.newSound(Gdx.files.internal("sesler/saribalonses.mp3"));
		siyahBalonSes = Gdx.audio.newSound(Gdx.files.internal("sesler/siyahbalonses.mp3"));

		bananaSes = Gdx.audio.newMusic(Gdx.files.internal("sesler/bananases.mp3"));
		dusmeSes = Gdx.audio.newMusic(Gdx.files.internal("sesler/dusmeses.mp3"));
		gruSes = Gdx.audio.newMusic(Gdx.files.internal("sesler/gruses.mp3"));
		menuSes = Gdx.audio.newMusic(Gdx.files.internal("sesler/menuses.mp3"));
		oyunSes = Gdx.audio.newMusic(Gdx.files.internal("sesler/oyunses.mp3"));

		menuSes.setLooping(true);
		oyunSes.setLooping(true);
		bananaSes.setLooping(false);
		dusmeSes.setLooping(false);
		gruSes.setLooping(false);
		oyunSes.setVolume(volume-0.5f);

		//Font Ayarları
		font = new BitmapFont();

		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("BauhausTr-Normal.otf"));
		FreeTypeFontGenerator.FreeTypeFontParameter parametre = new FreeTypeFontGenerator.FreeTypeFontParameter();
		parametre.size=156;
		parametre.borderWidth=8;
		parametre.color=Color.GOLDENROD;
		parametre.borderColor = Color.BLACK;
		font = generator.generateFont(parametre);

		//Patlatma
		dokunma = new Rectangle();
		dokunma.width = 1;
		dokunma.height = 1;

		muz = new Rectangle();
		muz.width = 50;
		muz.height = 100;
		muz.x = MathUtils.random(100, 1920 - 200);
		muz.y = 600;

		oynaButonu = new Rectangle();
		oynaButonu.width = 600;
		oynaButonu.height = 200;
		oynaButonu.x = 1140;
		oynaButonu.y = 815;

		sesButonu = new Rectangle();
		sesButonu.width = 600;
		sesButonu.height = 200;
		sesButonu.x = 1140;
		sesButonu.y = 565;

		hakkindaButonu = new Rectangle();
		hakkindaButonu.width = 600;
		hakkindaButonu.height = 200;
		hakkindaButonu.x = 1140;
		hakkindaButonu.y = 315;

		cikisButonu = new Rectangle();
		cikisButonu.width = 600;
		cikisButonu.height = 200;
		cikisButonu.x = 1140;
		cikisButonu.y = 65;

		menuButonu = new Rectangle();
		menuButonu.width = 450;
		menuButonu.height = 150;
		menuButonu.x = 1920/2-rsmMenuButonu.getWidth()/2;
		menuButonu.y = 900;

		rekor=score.getInteger("rekor", 0);

	}

	//içe aktardığımız resimleri müzikleri çalıştırır.
	//oyun çalıştığı müddetçe render methodu çalışır.
	@Override
	public void render() {

		if(Gdx.input.isTouched())
		{
			//(x,y,z) üç boyutlu vektör
			Vector3 dokunmaPozisyonu = new Vector3();

			//Vektörümüze dokunulan koordinatları veriyoruz
			dokunmaPozisyonu.set(Gdx.input.getX(),Gdx.input.getY(),0);

			//Koordinatları oyuna uyarlar
			kamera.unproject(dokunmaPozisyonu);

			//Dokunulan yerin koordinatlarını alma
			dokunma.x = dokunmaPozisyonu.x;
			dokunma.y = dokunmaPozisyonu.y;
		}
		else{
			dokunma.x = 3000;
			dokunma.y = 2000;
		}

		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		kamera.update();
		batch.setProjectionMatrix(kamera.combined);

		//////////////////////////////////////////////MENU SAHNESİ/////////////////////////////////////////////////
		if(ekran==0)
		{
			menuSes.play();

			sure=30;
			skor=0;
			toplamskor=0;
			kirmizipatladi=0;
			saripatladi=0;
			yesilpatladi=0;
			siyahpatladi=0;
			muz.x=MathUtils.random(100, 1920 - 200);
			muz.y=600;
			muzdonme=0;
			sariBalonZamani = TimeUtils.nanoTime();
			sonBalonZamani = TimeUtils.nanoTime();
			zamanlayici = TimeUtils.nanoTime();

			batch.begin();
			batch.draw(rsmMenuArkaplan,0,0);
			//////////////////////////////////////////////EN YUKSEK SKORU EKRANA BASAR//////////////////////////////////////////////
			font.draw(batch,"Rekor:" + rekor, 100, 800);

			batch.draw(rsmOynaButonu,1140,815);
			if(sesKontrol==1)
			{
				batch.draw(rsmSesiKapatButonu,1140,565);
			}

			if(sesKontrol==0)
			{
				batch.draw(rsmSesiAcButonu,1140,565);
			}
			batch.draw(rsmHakkindaButonu,1140,315);
			batch.draw(rsmCikisButonu,1140,65);

			batch.end();

			if (oynaButonu.overlaps(dokunma))
			{
				menuSes.stop();
				ekran=1;
			}

			if (sesButonu.overlaps(dokunma))
			{
				if(TimeUtils.nanoTime() - sesZamani > 500000000)
				{
					switch (sesKontrol)
					{
						case 0:
							volume = 1.0f;
							menuSes.setVolume(volume);
							oyunSes.setVolume(volume-0.7f);
							bananaSes.setVolume(volume);
							dusmeSes.setVolume(volume-0.5f);
							gruSes.setVolume(volume);
							sesKontrol = 1;
							break;
						case 1:
							volume = 0.0f;
							menuSes.setVolume(volume);
							oyunSes.setVolume(volume);
							bananaSes.setVolume(volume);
							dusmeSes.setVolume(volume);
							gruSes.setVolume(volume);
							sesKontrol = 0;
							break;
					}
					sesZamani = TimeUtils.nanoTime();
				}
			}

			if(hakkindaButonu.overlaps(dokunma))
			{
				ekran=4;
			}

			if(cikisButonu.overlaps(dokunma))
			{
				Gdx.app.exit();
			}
		}

		///////////////////////////////////////////////LEVEL 1 OYUN SAHNESİ//////////////////////////////////////////////////
		else if(ekran==1)
		{
			oyunSes.play();
			batch.begin();
			batch.draw(rsmSeviye1Arkaplan,0,0);

			for (Rectangle kirmiziBalon : kirmiziBalonlar) {
				batch.draw(rsmkirmiziBalon, kirmiziBalon.x, kirmiziBalon.y);
			}

			for (Rectangle sariBalon : sariBalonlar) {
				batch.draw(rsmsariBalon, sariBalon.x, sariBalon.y);
			}

			for (Rectangle siyahBalon : siyahBalonlar) {

				if (siyahRenkDegis == 0) {
					batch.draw(rsmyesilBalon, siyahBalon.x, siyahBalon.y);
				}
				if (siyahRenkDegis == 1 || siyahRenkDegis == 2) {
					batch.draw(rsmsiyahBalon, siyahBalon.x, siyahBalon.y);
				}
			}

			for (Rectangle yesilBalon : yesilBalonlar) {
				if (yesilRenkDegis == 0) {
					batch.draw(rsmsiyahBalon, yesilBalon.x, yesilBalon.y);
				}
				if (yesilRenkDegis == 1 || yesilRenkDegis == 2) {
					batch.draw(rsmyesilBalon, yesilBalon.x, yesilBalon.y);
				}
			}

			//////////////////////////////////////////////SKORU EKRANA BASAR//////////////////////////////////////////////
			font.draw(batch, "Skor:" + Integer.toString(skor), 50, 1030);
			//////////////////////////////////////////////SÜREYİ EKRANA BASAR//////////////////////////////////////////////
			font.draw(batch, "Süre:" + Integer.toString(sure), 1325, 1030);
			//////////////////////////////////////////////MENU BUTONUNU EKRANA BASAR////////////////////////////////////////
			batch.draw(rsmMenuButonu,1920/2-rsmMenuButonu.getWidth()/2,900);

			batch.end();

			//////////////////////////////////////////////1 SANİYEDE BİR İŞLEM YAPAR//////////////////////////////////////////////
			if(TimeUtils.nanoTime()- zamanlayici > 1000000000)
			{
				if(sure>0)
				{
					sure -=1;
				}
				else
				{
					sure = 0;
				}
				zamanlayici = TimeUtils.nanoTime();
			}

			//////////////////////////////////////////////1 SANİYEDE BİR BALONLAR ÜRETİR//////////////////////////////////////////////
			if(TimeUtils.nanoTime()- sonBalonZamani > 1000000000)
			{
				kirmiziYon = MathUtils.random(0,1);
				siyahRenkDegis = MathUtils.random(0,2);
				yesilRenkDegis = MathUtils.random(0,2);

				kirmiziBalonUret();
				siyahBalonUret();
				yesilBalonUret();
			}

			//////////////////////////////////////////////2 SANİYEDE BİR SARI BALON ÜRETİR//////////////////////////////////////////////
			if(TimeUtils.nanoTime() - sariBalonZamani > 2000000000)
			{
				sariBalonUret();
			}


			////////////////////////////////////////////////LEVEL 1 KIRMIZI BALONLARIN ARKAPLANDAKİ HAREKETİ//////////////////////////////////////////////
			Iterator<Rectangle> redBalon = kirmiziBalonlar.iterator();
			while (redBalon.hasNext())
			{
				Rectangle kirmiziBalon = redBalon.next();
				kirmiziBalon.y +=500 * Gdx.graphics.getDeltaTime();

				if(kirmiziYon==1)
				{
					kirmiziBalon.x +=500 * Gdx.graphics.getDeltaTime();
					if(kirmiziBalon.x > 1920-200)
					{
						kirmiziYon = 0;
					}
				}
				else if(kirmiziYon==0)
				{
					kirmiziBalon.x -=500 * Gdx.graphics.getDeltaTime();
					if(kirmiziBalon.x < 0)
					{
						kirmiziYon = 1;
					}
				}

				if(kirmiziBalon.y > balonsil)
				{
					redBalon.remove();
				}

				else if(kirmiziBalon.overlaps(dokunma))
				{
					kyBalonSes.play(volume);
					redBalon.remove();
					skor += 10;
					kirmizipatladi++;
				}

			}

			////////////////////////////////////////////////LEVEL 1 SARI BALONLARIN ARKAPLANDAKİ HAREKETİ//////////////////////////////////////////////
			Iterator<Rectangle> yellowBalon = sariBalonlar.iterator();
			while (yellowBalon.hasNext())
			{
				Rectangle sariBalon = yellowBalon.next();

				if(TimeUtils.nanoTime() - sariBalonZamani > 700000000)
				{
					yellowBalon.remove();
				}

				else if(sariBalon.overlaps(dokunma))
				{
					yellowBalon.remove();
					sariBalonSes.play(volume);
					skor += 20;
					saripatladi++;
				}
			}

			//////////////////////////////////////////////LEVEL 1 SİYAH BALONLARIN ARKAPLANDAKİ HAREKETİ//////////////////////////////////////////////
			Iterator<Rectangle> blackBalon = siyahBalonlar.iterator();
			while (blackBalon.hasNext())
			{
				Rectangle siyahBalon = blackBalon.next();
				siyahBalon.y +=200 * Gdx.graphics.getDeltaTime();

				if(siyahBalon.y > balonsil)
				{
					blackBalon.remove();
				}

				else if(siyahBalon.overlaps(dokunma))
				{
					if (siyahRenkDegis == 0)
					{
						kyBalonSes.play(volume);
						skor +=5;
						yesilpatladi++;
					}
					else if(siyahRenkDegis == 1 || siyahRenkDegis ==2)
					{
						siyahBalonSes.play(volume);
						siyahpatladi++;
						skor -=10;
					}
					blackBalon.remove();
				}
			}

			///////////////////////////////////////////////LEVEL 1 YESİL BALONLARIN ARKAPLANDAKİ HAREKETİ/////////////////////////////////////////////////
			Iterator<Rectangle> greenBalon = yesilBalonlar.iterator();
			while (greenBalon.hasNext())
			{
				Rectangle yesilBalon = greenBalon.next();
				yesilBalon.y +=400 * Gdx.graphics.getDeltaTime();

				if(yesilBalon.y > balonsil)
				{
					greenBalon.remove();
				}

				else if(yesilBalon.overlaps(dokunma))
				{
					if (yesilRenkDegis == 0)
					{
						siyahBalonSes.play(volume);
						siyahpatladi++;
						skor -=10;

					}
					else if(yesilRenkDegis == 1 || yesilRenkDegis ==2)
					{
						kyBalonSes.play(volume);
						skor +=5;
						yesilpatladi++;
					}
					greenBalon.remove();
				}
			}

			if (menuButonu.overlaps(dokunma))
			{
				ekran=0;
				oyunSes.stop();
				kirmiziBalonlar.clear();
				sariBalonlar.clear();
				siyahBalonlar.clear();
				yesilBalonlar.clear();
			}

			if(sure==0)
			{
				if(skor<100 || kirmizipatladi==0 || saripatladi==0 || yesilpatladi==0 || siyahpatladi==0)
				{
					ekran=6;
					toplamskor=skor+toplamskor;
				}
				else if(skor>=100 && kirmizipatladi>0 && saripatladi>0 && yesilpatladi>0 && siyahpatladi>0)
				{
					ekran=2;
					sure=30;
					toplamskor=skor+toplamskor;
					skor=0;
					kirmiziBalonlar.clear();
					sariBalonlar.clear();
					siyahBalonlar.clear();
					yesilBalonlar.clear();
				}
			}
		}

		///////////////////////////////////////////////LEVEL 2 OYUN SAHNESİ//////////////////////////////////////////////////
		else if (ekran == 2)
			{
				batch.begin();
				batch.draw(rsmSeviye2Arkaplan, 0, 0);

				for (Rectangle kirmiziBalon : kirmiziBalonlar) {
					batch.draw(rsmkirmiziBalon, kirmiziBalon.x, kirmiziBalon.y);
				}

				for (Rectangle sariBalon : sariBalonlar) {
					batch.draw(rsmsariBalon, sariBalon.x, sariBalon.y);
				}

				for (Rectangle siyahBalon : siyahBalonlar) {
					if (siyahRenkDegis == 0) {
						batch.draw(rsmyesilBalon, siyahBalon.x, siyahBalon.y);
					}
					if (siyahRenkDegis == 1 || siyahRenkDegis == 2) {
						batch.draw(rsmsiyahBalon, siyahBalon.x, siyahBalon.y);
					}
				}

				for (Rectangle yesilBalon : yesilBalonlar) {
					if (yesilRenkDegis == 0) {
						batch.draw(rsmsiyahBalon, yesilBalon.x, yesilBalon.y);
					}
					if (yesilRenkDegis == 1 || yesilRenkDegis == 2) {
						batch.draw(rsmyesilBalon, yesilBalon.x, yesilBalon.y);
					}
				}

				//////////////////////////////////////////////SKORU EKRANA BASAR//////////////////////////////////////////////
				font.draw(batch, "Skor:" + Integer.toString(skor), 50, 1030);
				//////////////////////////////////////////////SÜREYİ EKRANA BASAR//////////////////////////////////////////////
				font.draw(batch, "Süre:" + Integer.toString(sure), 1325, 1030);
				//////////////////////////////////////////////MENU BUTONUNU EKRANA BASAR////////////////////////////////////////
				batch.draw(rsmMenuButonu, 1920 / 2 - rsmMenuButonu.getWidth() / 2, 900);
				//////////////////////////////////////////////5 SANİYEDE BİRMUZU 3 SANİYE EKRANA BASAR///////////////////////////
				if (sure % 8 == 4)
				{
					bananaSes.play();
					batch.draw(rsmMuzcuMinyon, muz.x - 100, 700);
				}
				if (sure % 8 == 1 || sure % 8 == 2 || sure % 8 == 3)
				{
					sprite.setPosition(muz.x,muz.y);
					sprite.setSize(50,100);
					sprite.setOrigin(25,50);
					sprite.setRotation(muzdonme+=10);

					dusmeSes.play();
					batch.draw(rsmMuzcuMinyon2, muz.x - 100, 700);
					sprite.draw(batch);
					muz.y -= 260 * Gdx.graphics.getDeltaTime();
				}

				batch.end();

				//////////////////////////////////////////////1 SANİYEDE BİR İŞLER YAPAR//////////////////////////////////////////////
				if (TimeUtils.nanoTime() - zamanlayici > 1000000000) {
					if (sure > 0) {
						sure -= 1;
					} else {
						sure = 0;
					}
					zamanlayici = TimeUtils.nanoTime();
				}
				//////////////////////////////////////////////0.75 SANİYEDE BİR BALONLAR ÜRETİR//////////////////////////////////////////////
				if (TimeUtils.nanoTime() - sonBalonZamani > 750000000) {
					kirmiziYon = MathUtils.random(0, 1);
					siyahRenkDegis = MathUtils.random(0, 2);
					yesilRenkDegis = MathUtils.random(0, 2);

					kirmiziBalonUret();
					siyahBalonUret();
					yesilBalonUret();
				}

				//////////////////////////////////////////////2 SANİYEDE BİR SARI BALON ÜRETİR//////////////////////////////////////////////
				if (TimeUtils.nanoTime() - sariBalonZamani > 1500000000) {
					sariBalonUret();
				}


				////////////////////////////////////////////////LEVEL 2 KIRMIZI BALONLARIN ARKAPLANDAKİ HAREKETİ//////////////////////////////////////////////
				Iterator<Rectangle> redBalon = kirmiziBalonlar.iterator();
				while (redBalon.hasNext()) {
					Rectangle kirmiziBalon = redBalon.next();
					kirmiziBalon.y += 700 * Gdx.graphics.getDeltaTime();

					if (kirmiziYon == 1) {
						kirmiziBalon.x += 500 * Gdx.graphics.getDeltaTime();
						if (kirmiziBalon.x > 1920 - 200) {
							kirmiziYon = 0;
						}
					} else if (kirmiziYon == 0) {
						kirmiziBalon.x -= 500 * Gdx.graphics.getDeltaTime();
						if (kirmiziBalon.x < 0) {
							kirmiziYon = 1;
						}
					}

					if (kirmiziBalon.y > balonsil) {
						redBalon.remove();
					} else if (kirmiziBalon.overlaps(dokunma)) {
						kyBalonSes.play(volume);
						redBalon.remove();
						skor += 10;
					} else if (kirmiziBalon.overlaps(muz)) {
						redBalon.remove();
					}
				}

				////////////////////////////////////////////////LEVEL 2 SARI BALONLARIN ARKAPLANDAKİ HAREKETİ//////////////////////////////////////////////
				Iterator<Rectangle> yellowBalon = sariBalonlar.iterator();
				while (yellowBalon.hasNext()) {
					Rectangle sariBalon = yellowBalon.next();

					if (TimeUtils.nanoTime() - sariBalonZamani > 650000000) {
						yellowBalon.remove();
					} else if (sariBalon.overlaps(dokunma)) {
						yellowBalon.remove();
						sariBalonSes.play(volume);
						skor += 20;
					} else if (sariBalon.overlaps(muz)) {
						yellowBalon.remove();
					}
				}

				//////////////////////////////////////////////LEVEL 2 SİYAH BALONLARIN ARKAPLANDAKİ HAREKETİ//////////////////////////////////////////////
				Iterator<Rectangle> blackBalon = siyahBalonlar.iterator();
				while (blackBalon.hasNext()) {
					Rectangle siyahBalon = blackBalon.next();
					siyahBalon.y += 400 * Gdx.graphics.getDeltaTime();

					if (siyahBalon.y > balonsil) {
						blackBalon.remove();
					} else if (siyahBalon.overlaps(dokunma)) {
						if (siyahRenkDegis == 0) {
							kyBalonSes.play(volume);
							skor += 5;
						} else if (siyahRenkDegis == 1 || siyahRenkDegis == 2) {
							siyahBalonSes.play(volume);
							skor -= 10;

						}
						blackBalon.remove();
					} else if (siyahBalon.overlaps(muz)) {
						blackBalon.remove();
					}
				}

				///////////////////////////////////////////////LEVEL 2 YESİL BALONLARIN ARKAPLANDAKİ HAREKETİ/////////////////////////////////////////////////
				Iterator<Rectangle> greenBalon = yesilBalonlar.iterator();
				while (greenBalon.hasNext()) {
					Rectangle yesilBalon = greenBalon.next();
					yesilBalon.y += 600 * Gdx.graphics.getDeltaTime();

					if (yesilBalon.y > balonsil) {
						greenBalon.remove();
					} else if (yesilBalon.overlaps(dokunma)) {
						if (yesilRenkDegis == 0) {
							siyahBalonSes.play(volume);
							skor -= 10;

						} else if (yesilRenkDegis == 1 || yesilRenkDegis == 2) {
							kyBalonSes.play(volume);
							skor += 5;
						}
						greenBalon.remove();
					} else if (yesilBalon.overlaps(muz)) {
						greenBalon.remove();
					}
				}

				///////////////////////////////////////////MUZU YENİDEN KONUMLANDIRIR/////////////////////////////////////////////
				if (sure % 8 == 0) {
					muz.x = MathUtils.random(100, 1920 - 200);
					muz.y = 600;
				}

				if (menuButonu.overlaps(dokunma)) {
					ekran = 0;
					oyunSes.stop();
					kirmiziBalonlar.clear();
					sariBalonlar.clear();
					siyahBalonlar.clear();
					yesilBalonlar.clear();
				}

				if (sure == 0 && skor < 100)
				{
					ekran = 6;
					toplamskor = skor + toplamskor;
				}
				else if (sure == 0 && skor >= 100)
				{
					ekran = 3;
					sure = 30;
					toplamskor = skor + toplamskor;
					skor = 0;
					kirmiziBalonlar.clear();
					sariBalonlar.clear();
					siyahBalonlar.clear();
					yesilBalonlar.clear();
				}
			}

			///////////////////////////////////////////////LEVEL 3 OYUN SAHNESİ//////////////////////////////////////////////////

			else if (ekran == 3) {
				batch.begin();
				batch.draw(rsmSeviye3Arkaplan, 0, 0);

				for (Rectangle kirmiziBalon : kirmiziBalonlar)
				{
					if (sure % 8 == 1 || sure % 8 == 2 || sure % 8 == 3)
					{
						batch.draw(rsmkirmiziBalon, kirmiziBalon.x, kirmiziBalon.y, 100, 100);
					} else
					{
						batch.draw(rsmkirmiziBalon, kirmiziBalon.x, kirmiziBalon.y);
					}
				}

				for (Rectangle sariBalon : sariBalonlar)
				{
					if (sure % 8 == 1 || sure % 8 == 2 || sure % 8 == 3)
					{
						batch.draw(rsmsariBalon, sariBalon.x, sariBalon.y, 100, 100);
					} else
					{
						batch.draw(rsmsariBalon, sariBalon.x, sariBalon.y);
					}
				}

				for (Rectangle siyahBalon : siyahBalonlar)
				{
					if (sure % 8 == 1 || sure % 8 == 2 || sure % 8 == 3)
					{
						if (siyahRenkDegis == 0)
						{
							batch.draw(rsmyesilBalon, siyahBalon.x, siyahBalon.y, 100, 100);
						}
						if (siyahRenkDegis == 1 || siyahRenkDegis == 2)
						{
							batch.draw(rsmsiyahBalon, siyahBalon.x, siyahBalon.y, 100, 100);
						}
					}
					else
					{
						if (siyahRenkDegis == 0)
						{
							batch.draw(rsmyesilBalon, siyahBalon.x, siyahBalon.y);
						}
						if (siyahRenkDegis == 1 || siyahRenkDegis == 2)
						{
							batch.draw(rsmsiyahBalon, siyahBalon.x, siyahBalon.y);
						}
					}
				}

				for (Rectangle yesilBalon : yesilBalonlar) {
					if (sure % 8 == 1 || sure % 8 == 2 || sure % 8 == 3) {
						if (yesilRenkDegis == 0) {
							batch.draw(rsmsiyahBalon, yesilBalon.x, yesilBalon.y, 100, 100);
						}
						if (yesilRenkDegis == 1 || yesilRenkDegis == 2) {
							batch.draw(rsmyesilBalon, yesilBalon.x, yesilBalon.y, 100, 100);
						}
					} else {
						if (yesilRenkDegis == 0) {
							batch.draw(rsmsiyahBalon, yesilBalon.x, yesilBalon.y);
						}
						if (yesilRenkDegis == 1 || yesilRenkDegis == 2) {
							batch.draw(rsmyesilBalon, yesilBalon.x, yesilBalon.y);
						}
					}
				}

				//////////////////////////////////////////////SKORU EKRANA BASAR//////////////////////////////////////////////
				font.draw(batch, "Skor:" + Integer.toString(skor), 50, 1030);
				//////////////////////////////////////////////SÜREYİ EKRANA BASAR//////////////////////////////////////////////
				font.draw(batch, "Süre:" + Integer.toString(sure), 1325, 1030);
				//////////////////////////////////////////////MENU BUTONUNU EKRANA BASAR////////////////////////////////////////
				batch.draw(rsmMenuButonu, 1920 / 2 - rsmMenuButonu.getWidth() / 2, 900);
				//////////////////////////////5 SANİYEDE BİR 3 SANİYE BOYUNCA GRU'YU EKRANA BASAR///////////////////////////////
				if (sure % 8 == 1 || sure % 8 == 2 || sure % 8 == 3)
				{
					if (sure % 8 == 3)
					{
						gruSes.play();
					}
					batch.draw(rsmGru, 1920 / 2 - rsmGru.getWidth() / 2, 700);
				}

				batch.end();

				//////////////////////////////////////////////1 SANİYEDE BİR İŞLER YAPAR//////////////////////////////////////////////
				if (TimeUtils.nanoTime() - zamanlayici > 1000000000) {
					if (sure > 0) {
						sure -= 1;
					} else {
						sure = 0;
					}
					zamanlayici = TimeUtils.nanoTime();
				}
				//////////////////////////////////////////////0.5 SANİYEDE BİR BALONLAR ÜRETİR//////////////////////////////////////////////
				if (TimeUtils.nanoTime() - sonBalonZamani > 500000000) {
					kirmiziYon = MathUtils.random(0, 1);
					siyahRenkDegis = MathUtils.random(0, 2);
					yesilRenkDegis = MathUtils.random(0, 2);

					kirmiziBalonUret();
					siyahBalonUret();
					yesilBalonUret();
				}

				//////////////////////////////////////////////2 SANİYEDE BİR SARI BALON ÜRETİR//////////////////////////////////////////////
				if (TimeUtils.nanoTime() - sariBalonZamani > 750000000) {
					sariBalonUret();
				}

				////////////////////////////////////////////////LEVEL 3 KIRMIZI BALONLARIN ARKAPLANDAKİ HAREKETİ//////////////////////////////////////////////
				Iterator<Rectangle> redBalon = kirmiziBalonlar.iterator();
				while (redBalon.hasNext()) {
					Rectangle kirmiziBalon = redBalon.next();
					kirmiziBalon.y += 900 * Gdx.graphics.getDeltaTime();


					if (kirmiziYon == 1) {
						kirmiziBalon.x += 500 * Gdx.graphics.getDeltaTime();
						if (kirmiziBalon.x > 1920 - 200) {
							kirmiziYon = 0;
						}
					} else if (kirmiziYon == 0) {
						kirmiziBalon.x -= 500 * Gdx.graphics.getDeltaTime();
						if (kirmiziBalon.x < 0) {
							kirmiziYon = 1;
						}
					}

					if (kirmiziBalon.y > balonsil)
					{
						redBalon.remove();
					}
					else if (kirmiziBalon.overlaps(dokunma))
					{
						kyBalonSes.play(volume);
						redBalon.remove();
						skor += 10;
					}
				}

				////////////////////////////////////////////////LEVEL 3 SARI BALONLARIN ARKAPLANDAKİ HAREKETİ//////////////////////////////////////////////
				Iterator<Rectangle> yellowBalon = sariBalonlar.iterator();
				while (yellowBalon.hasNext()) {
					Rectangle sariBalon = yellowBalon.next();

					if (TimeUtils.nanoTime() - sariBalonZamani > 550000000) {
						yellowBalon.remove();
					} else if (sariBalon.overlaps(dokunma)) {
						yellowBalon.remove();
						sariBalonSes.play(volume);
						skor += 20;
					}
				}

				//////////////////////////////////////////////LEVEL 3 SİYAH BALONLARIN ARKAPLANDAKİ HAREKETİ//////////////////////////////////////////////
				Iterator<Rectangle> blackBalon = siyahBalonlar.iterator();
				while (blackBalon.hasNext()) {
					Rectangle siyahBalon = blackBalon.next();
					siyahBalon.y += 600 * Gdx.graphics.getDeltaTime();

					if (siyahBalon.y > balonsil) {
						blackBalon.remove();
					} else if (siyahBalon.overlaps(dokunma)) {
						if (siyahRenkDegis == 0) {
							kyBalonSes.play(volume);
							skor += 5;
						} else if (siyahRenkDegis == 1 || siyahRenkDegis == 2) {
							siyahBalonSes.play(volume);
							skor -= 10;

						}
						blackBalon.remove();
					}
				}

				///////////////////////////////////////////////LEVEL 3 YESİL BALONLARIN ARKAPLANDAKİ HAREKETİ/////////////////////////////////////////////////
				Iterator<Rectangle> greenBalon = yesilBalonlar.iterator();
				while (greenBalon.hasNext()) {
					Rectangle yesilBalon = greenBalon.next();
					yesilBalon.y += 800 * Gdx.graphics.getDeltaTime();

					if (yesilBalon.y > balonsil) {
						greenBalon.remove();
					} else if (yesilBalon.overlaps(dokunma)) {
						if (yesilRenkDegis == 0) {
							siyahBalonSes.play(volume);
							skor -= 10;
						} else if (yesilRenkDegis == 1 || yesilRenkDegis == 2) {
							kyBalonSes.play(volume);
							skor += 5;
						}
						greenBalon.remove();
					}
				}

				if (menuButonu.overlaps(dokunma)) {
					ekran = 0;
					oyunSes.stop();
					kirmiziBalonlar.clear();
					sariBalonlar.clear();
					siyahBalonlar.clear();
					yesilBalonlar.clear();
				}

				if (sure == 0 && skor < 100)
				{
					ekran = 6;
					toplamskor = skor + toplamskor;
				}
				else if (sure == 0 && skor >= 100)
				{
					ekran = 5;
					toplamskor = skor + toplamskor;

					if (toplamskor > rekor)
					{
						rekor = toplamskor;
						score.putInteger("rekor", rekor);
						score.flush();
						rekor = score.getInteger("rekor", 0);
					}
				}
			}

		if(ekran==4)
		{
			batch.begin();
			batch.draw(rsmHakkindaArkaplan,0,0);
			//////////////////////////////////////////////MENU BUTONUNU EKRANA BASAR////////////////////////////////////////
			batch.draw(rsmMenuButonu,1920/2-rsmMenuButonu.getWidth()/2,910);
			batch.end();

			if (menuButonu.overlaps(dokunma))
			{
				ekran=0;
				kirmiziBalonlar.clear();
				sariBalonlar.clear();
				siyahBalonlar.clear();
				yesilBalonlar.clear();
			}
		}

		if(ekran==5)
		{
			batch.begin();
			batch.draw(rsmBasariliArkaplan,0,0);
			//////////////////////////////////////////////MENU BUTONUNU EKRANA BASAR////////////////////////////////////////
			if(toplamskor<rekor)
			{
				font.draw(batch,"Toplam Skor", 900, 700);
				font.draw(batch,Integer.toString(toplamskor), 1200,550);
			}
			else if(toplamskor>=rekor)
			{
				font.draw(batch,"Tebrikler!", 1000, 750);
				font.draw(batch,"Yeni Rekor", 950, 600);
				font.draw(batch,Integer.toString(toplamskor), 1200,450);
			}
			batch.draw(rsmMenuButonu,1920/2-rsmMenuButonu.getWidth()/2,900);
			batch.end();

			if (menuButonu.overlaps(dokunma))
			{
				ekran=0;
				oyunSes.stop();
				kirmiziBalonlar.clear();
				sariBalonlar.clear();
				siyahBalonlar.clear();
				yesilBalonlar.clear();
			}
		}

		if(ekran==6)
		{
			batch.begin();
			batch.draw(rsmBasarisizArkaplan,0,0);
			//////////////////////////////////////////////MENU BUTONUNU EKRANA BASAR////////////////////////////////////////
			batch.draw(rsmMenuButonu,1920/2-rsmMenuButonu.getWidth()/2,900);
			font.draw(batch,"Toplam Skor",100,800);
			font.draw(batch,Integer.toString(toplamskor),450,650);

			if(kirmizipatladi==0 || saripatladi ==0 || yesilpatladi==0 || siyahpatladi==0)
			{
				batch.draw(rsmyesilBalon,75,175);
				font.draw(batch,"" + yesilpatladi,135,150);
				batch.draw(rsmsariBalon,300,175);
				font.draw(batch,"" + saripatladi,360,150);
				batch.draw(rsmkirmiziBalon,525,175);
				font.draw(batch,"" + kirmizipatladi,585,150);
				batch.draw(rsmsiyahBalon,750,175);
				font.draw(batch,"" + siyahpatladi,810,150);
			}
			batch.end();

			if (menuButonu.overlaps(dokunma))
			{
				ekran=0;
				oyunSes.stop();
				kirmiziBalonlar.clear();
				sariBalonlar.clear();
				siyahBalonlar.clear();
				yesilBalonlar.clear();
			}
		}
	}

	//////////////////////////////////////////////////////BELLEK TEMİZLER////////////////////////////////////////////////////
	@Override
	public void dispose() {
		rsmkirmiziBalon.dispose();
		rsmsariBalon.dispose();
		rsmsiyahBalon.dispose();
		rsmyesilBalon.dispose();
		rsmMenuArkaplan.dispose();
		rsmSeviye1Arkaplan.dispose();
		rsmSeviye2Arkaplan.dispose();
		rsmSeviye3Arkaplan.dispose();
		rsmBasariliArkaplan.dispose();
		rsmBasarisizArkaplan.dispose();
		rsmHakkindaArkaplan.dispose();
		rsmOynaButonu.dispose();
		rsmSesiAcButonu.dispose();
		rsmSesiKapatButonu.dispose();
		rsmHakkindaButonu.dispose();
		rsmCikisButonu.dispose();
		rsmMenuButonu.dispose();
		rsmGru.dispose();
		rsmMuzcuMinyon.dispose();
		rsmMuz.dispose();
		kyBalonSes.dispose();
		sariBalonSes.dispose();
		siyahBalonSes.dispose();
		bananaSes.dispose();
		dusmeSes.dispose();
		gruSes.dispose();
		menuSes.dispose();
		oyunSes.dispose();
		font.dispose();
		batch.dispose();
	}


	@Override
	public void resize(int width, int height) {

	}

	@Override
	public void pause() {

	}

	@Override
	public void resume() {

	}

}
