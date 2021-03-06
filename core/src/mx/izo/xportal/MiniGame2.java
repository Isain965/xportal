package mx.izo.xportal;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.ArrayList;


/**
 * Created by Equipo alfa buena maravilla onda dinamita escuadrón lobo on 10/11/2016.
 */

public class MiniGame2 implements Screen{

    //PREFERENCIAS
    public Preferences niveles = Gdx.app.getPreferences("Niveles");
    public Preferences sonidos = Gdx.app.getPreferences("Sonidos");
    public Preferences musica = Gdx.app.getPreferences("Musica");
    public Preferences score = Gdx.app.getPreferences("Score");
    public Preferences siguienteNivel = Gdx.app.getPreferences("SiguienteNivel");

    private mx.izo.xportal.PantallaCargando pantallaCargando;

    public static final float ANCHO_MAPA = 1280;   // Ancho del mapa en pixeles
    public static final float ALTO_MAPA = 896;

    // Referencia al objeto de tipo Game (tiene setScreen para cambiar de pantalla)
    private Plataforma plataforma;

    // La cámara y vista principal
    private OrthographicCamera camara;
    private Viewport vista;

    // Objeto para dibujar en la pantalla
    private SpriteBatch batch;

    private Sprite spriteVidas;
    private Sprite spriteVidasF;

    // MAPA
    private TiledMap mapa;      // Información del mapa en memoria
    private OrthogonalTiledMapRenderer rendererMapa;    // Objeto para dibujar el mapa

    // Personaje
    private Texture texturaPersonaje;       // Aquí cargamos la imagen marioSprite.png con varios frames
    private Texture texturaPersonaje2;
    private mx.izo.xportal.Personaje mario;
    public static final int TAM_CELDA = 32;

    //Musica de fondo
    private Music musicFondo;


    // HUD. Los componentes en la pantalla que no se mueven
    private OrthographicCamera camaraHUD;   // Cámara fija
    // Botones izquierda/derecha
    private Texture texturaBtnIzquierda;
    private Boton btnIzquierda;
    private Texture texturaBtnDerecha;
    private Boton btnDerecha;
    // Botón saltar
    private Texture texturaSalto;
    private Boton btnSalto;



    //Boton disparar
    private Texture texturaDisparo;
    private Boton btnDisparo;

    //Boton Pausa
    private Texture texturaBtnPausa;
    private Boton btnPausa;



    // Estrellas recolectadas
    private int estrellas;
    private int vidaf =3;
    private int vidafMax=5;
    private int vidafMin=0;
    private Texto texto;
    private Sound sonidoEstrella, sonidoLlave,sonidoPistola,sonidoRetrocarga,mute;
    private int enemD = 12;


    // Fin del juego, Gana o Pierde
    private Texture texturaGana;
    private Boton btnGana;
    private Sound sonidoPierde, sonidoVida;

    //textura barra de vidas
    private Texture texturaVidas;
    private Texture texturaVidasF;

    private Texture texturaBala;
    private Texture texturaBalaPlanta;
    private Texture texturaBalaEmbudo;

    //private Texture vidas;

    private Texture texturaEnemigo;
    private Texture texturaEnemigo2;
    private Texture alien1;//, alien2, alien3;

    private int tiempoDisparo=2;

    private float tiempoJuego=0;

    // Estados del juego
    private EstadosJuego estadoJuego;

    ArrayList<Bala> balas = new ArrayList<Bala>();
    ArrayList<mx.izo.xportal.Enemigo> enemigos = new ArrayList<mx.izo.xportal.Enemigo>();
    ArrayList<EnemigoV> enemigosV = new ArrayList<EnemigoV>();
    //Balas enemigos
    ArrayList<Bala> balasEnemigos = new ArrayList<Bala>();
    ArrayList<BalaV> balasEnemigosV = new ArrayList<BalaV>();

    private boolean llaveA = false;
    private boolean llaveB = false;

    private int rango = 300;
    private  Bala balaAnterior;
    private  Bala balaAnteriorV;

    private boolean banderaDisparo=true;

    private boolean banderaArma = false;

    private boolean banderaDireccion=false;


    //TEXURAS PARA LA PAUSA
    private Texture texturaPausa;
    private Texture texturaMenu;
    private Texture texturaSonidoT;
    private Texture texturaMusicaT;
    private Texture texturaSonidoF;
    private Texture texturaMusicaF;
    private Texture texturaPlay;
    private Boton btnPantallaPausa;
    private Boton btnMenu;
    private Boton btnPlay;
    private Boton btnSonidoT;
    private Boton btnMusicaT;
    private Boton btnSonidoF;
    private Boton btnMusicaF;
    private boolean banderaPausa = false;

    //Textura para cuando pierde
    private Texture texturaPierde;
    private Texture texturaMenuP;
    private Texture texturaPlayP;
    private Boton btnPierde;
    private Boton btnMenuP;
    private Boton btnPlayP;
    private boolean haPerdio = false;

    private boolean estadoMusica = musica.getBoolean("estadoMusica");
    private boolean estadoSonidos = sonidos.getBoolean("estadoSonidos");

    public MiniGame2(Plataforma plataforma) {
        this.plataforma = plataforma;
    }

    /*
    Se ejecuta al mostrar este Screen como pantalla de la app
     */
    @Override
    public void show() {
        // Crea la cámara/vista
        camara = new OrthographicCamera(Plataforma.ANCHO_CAMARA,Plataforma.ALTO_CAMARA);
        camara.position.set(Plataforma.ANCHO_CAMARA / 2, Plataforma.ALTO_CAMARA / 2, 0);
        camara.update();
        vista = new StretchViewport(Plataforma.ANCHO_CAMARA, Plataforma.ALTO_CAMARA, camara);

        batch = new SpriteBatch();

        // Cámara para HUD
        camaraHUD = new OrthographicCamera(Plataforma.ANCHO_CAMARA,Plataforma.ALTO_CAMARA);
        camaraHUD.position.set(Plataforma.ANCHO_CAMARA / 2, Plataforma.ALTO_CAMARA / 2, 0);
        camaraHUD.update();

        //cargarRecursos();
        crearObjetos();

        // Indicar el objeto que atiende los eventos de touch (entrada en general)
        Gdx.input.setInputProcessor(new ProcesadorEntrada());

        // Tecla BACK (Android)
        Gdx.input.setCatchBackKey(true);


        estadoJuego = EstadosJuego.JUGANDO;

        // Texto
        texto = new Texto();
    }

    private void crearObjetos() {
        AssetManager assetManager = plataforma.getAssetManager();   // Referencia al assetManager
        // Carga el mapa en memoria
        mapa = assetManager.get("inv.tmx");
        //mapa.getLayers().get(0).setVisible(false);    // Pueden ocultar una capa así
        // Crear el objeto que dibujará el mapa

        rendererMapa = new OrthogonalTiledMapRenderer(mapa,batch);
        rendererMapa.setView(camara);
        // Cargar frames
        texturaPersonaje = assetManager.get("nave.png");
        //texturaPersonaje2 = assetManager.get("marioSpriteIzq.png");

        texturaSalto = assetManager.get("salto.png");
        //textura barra de vidas
        texturaVidas = assetManager.get("barra.png");
        texturaVidasF = assetManager.get("barraF.png");

        //dibuja barra vidas
        spriteVidas = new Sprite(texturaVidas);
        spriteVidasF = new Sprite(texturaVidasF);
        //spriteVidas.setPosition(100,ALTO_MAPA/2);

        // Crear el personaje
        mario = new mx.izo.xportal.Personaje(texturaPersonaje);
        // Posición inicial del personaje
        mario.setVelocidadX(4);
        mario.getSprite().setPosition(Plataforma.ANCHO_CAMARA / 2,50);


        // Crear los botones
        texturaBtnIzquierda = assetManager.get("BtmIzquierdo.png");
        btnIzquierda = new Boton(texturaBtnIzquierda);
        btnIzquierda.setPosicion(TAM_CELDA,TAM_CELDA );
        btnIzquierda.setAlfa(0.7f); // Un poco de transparencia
        texturaBtnDerecha = assetManager.get("BtmDerecho.png");
        btnDerecha = new Boton(texturaBtnDerecha);
        btnDerecha.setPosicion(6 * TAM_CELDA,  TAM_CELDA);
        btnDerecha.setAlfa(0.7f); // Un poco de transparencia
        //texturaSalto = assetManager.get("BtmSaltar.png");
        //btnSalto = new Boton(texturaSalto);
        //btnSalto.setPosicion(Plataforma.ANCHO_CAMARA - 4.5f * TAM_CELDA,  TAM_CELDA);
        //btnSalto.setAlfa(0.7f);

        texturaDisparo = assetManager.get("shoot.png");
        btnDisparo = new Boton(texturaDisparo);
        //btnDisparo.setPosicion(Plataforma.ANCHO_CAMARA/2, Plataforma.ALTO_CAMARA/2);
        btnDisparo.setPosicion(Plataforma.ANCHO_CAMARA - 4.5f * TAM_CELDA,  TAM_CELDA);
        btnDisparo.setAlfa(0.7f);

        texturaBtnPausa = assetManager.get("BtmPausa.png");
        btnPausa = new Boton(texturaBtnPausa);
        btnPausa.setPosicion(Plataforma.ANCHO_CAMARA - 4.5f * TAM_CELDA,  TAM_CELDA+570);
        btnPausa.setAlfa(0.7f);

        // Gana
        texturaGana = assetManager.get("ganaste.png");
        btnGana = new Boton(texturaGana);
        btnGana.setPosicion(Plataforma.ANCHO_CAMARA/2-btnGana.getRectColision().width/2,Plataforma.ALTO_CAMARA/2-btnGana.getRectColision().height/2);
        btnGana.setAlfa(0.7f);


        texturaBala = assetManager.get("bala.png");
        texturaBalaEmbudo = assetManager.get("balaA.png");
        texturaBalaPlanta = assetManager.get("balaPlanta.png");

        //vidas=assetManager.get("pil.png");
        //vidas.draw(plataforma,100,100);

        texturaEnemigo = assetManager.get("Planta.png");
        texturaEnemigo2 = assetManager.get("embudo.png");
        alien1 = assetManager.get("alien1.png");
        //alien2 = assetManager.get("alien2.png");
        //alien3 = assetManager.get("alien3.png");

        /*Enemigo enemigo1 = new Enemigo(texturaEnemigo);
        enemigo1.setPosicion(1142,20);
        Enemigo enemigo2 = new Enemigo(texturaEnemigo);
        enemigo2.setPosicion(2284,20);
        Enemigo enemigo3 = new Enemigo(texturaEnemigo);
        enemigo3.setPosicion(3426,20);
        enemigos.add(enemigo1);
        enemigos.add(enemigo2);
        enemigos.add(enemigo3);
        balaAnterior = new Bala(texturaBalaPlanta);
        balaAnterior.setPosicion(enemigo1.getX(),641);*/

        EnemigoV enemigoV1 = new EnemigoV(alien1,2);
        //enemigoV1 = new EnemigoV(alien1,2);
        enemigoV1.setPosicion(ANCHO_MAPA/2-enemigoV1.getSprite().getWidth()*2,ALTO_MAPA/2);
        EnemigoV enemigoV2 = new EnemigoV(alien1,2);
        enemigoV2.setPosicion(ANCHO_MAPA/2-(enemigoV2.getSprite().getWidth()*4),ALTO_MAPA/2);
        EnemigoV enemigoV3 = new EnemigoV(alien1,2);
        enemigoV3.setPosicion(ANCHO_MAPA/2+(enemigoV3.getSprite().getWidth()),ALTO_MAPA/2);
        EnemigoV enemigoV4 = new EnemigoV(alien1,2);
        //enemigoV4 = new EnemigoV(alien1,2);
        enemigoV4.setPosicion(ANCHO_MAPA/2+(enemigoV4.getSprite().getWidth()*3),ALTO_MAPA/2);
        EnemigoV enemigoV5 = new EnemigoV(alien1,2);
        enemigoV5.setPosicion(ANCHO_MAPA/2-enemigoV5.getSprite().getWidth()*2,ALTO_MAPA/3);
        EnemigoV enemigoV6 = new EnemigoV(alien1,2);
        enemigoV6.setPosicion(ANCHO_MAPA/2-(enemigoV6.getSprite().getWidth()*4),ALTO_MAPA/3);
        EnemigoV enemigoV7 = new EnemigoV(alien1,2);
        enemigoV7.setPosicion(ANCHO_MAPA/2+(enemigoV7.getSprite().getWidth()),ALTO_MAPA/3);
        EnemigoV enemigoV8 = new EnemigoV(alien1,2);
        enemigoV8.setPosicion(ANCHO_MAPA/2+(enemigoV8.getSprite().getWidth()*3),ALTO_MAPA/3);
        EnemigoV enemigoV9 = new EnemigoV(alien1,2);
        enemigoV9.setPosicion(ANCHO_MAPA/2-enemigoV9.getSprite().getWidth()*2,ALTO_MAPA/3+enemigoV9.getSprite().getHeight()*4);
        EnemigoV enemigoV10 = new EnemigoV(alien1,2);
        enemigoV10.setPosicion(ANCHO_MAPA/2-(enemigoV10.getSprite().getWidth()*4),ALTO_MAPA/3+enemigoV10.getSprite().getHeight()*4);
        EnemigoV enemigoV11 = new EnemigoV(alien1,2);
        enemigoV11.setPosicion(ANCHO_MAPA/2+(enemigoV11.getSprite().getWidth()),ALTO_MAPA/3+enemigoV11.getSprite().getHeight()*4);
        EnemigoV enemigoV12 = new EnemigoV(alien1,2);
        enemigoV12.setPosicion(ANCHO_MAPA/2+(enemigoV12.getSprite().getWidth()*3),ALTO_MAPA/3+enemigoV12.getSprite().getHeight()*4);

        enemigosV.add(enemigoV1);
        enemigosV.add(enemigoV2);
        enemigosV.add(enemigoV3);
        enemigosV.add(enemigoV4);
        enemigosV.add(enemigoV5);
        enemigosV.add(enemigoV6);
        enemigosV.add(enemigoV7);
        enemigosV.add(enemigoV8);
        enemigosV.add(enemigoV9);
        enemigosV.add(enemigoV10);
        enemigosV.add(enemigoV11);
        enemigosV.add(enemigoV12);
        balaAnteriorV = new Bala(texturaBalaEmbudo);
        balaAnteriorV.setPosicion(enemigoV1.getX(),641);


        // Efecto moneda
        if(estadoSonidos) {
            sonidoEstrella = assetManager.get("monedas.mp3");
            sonidoPierde = assetManager.get("opendoor.mp3");
            sonidoVida = assetManager.get("vidawi.mp3");
            sonidoLlave = assetManager.get("llave.mp3");
            sonidoPistola = assetManager.get("pistola.mp3");
            sonidoRetrocarga = assetManager.get("retrocarga.wav");
            mute = assetManager.get("Mute.mp3");
        }else{
            sonidoEstrella = assetManager.get("Mute.mp3");
            sonidoPierde = assetManager.get("Mute.mp3");
            sonidoVida = assetManager.get("Mute.mp3");
            sonidoLlave = assetManager.get("Mute.mp3");
            sonidoPistola = assetManager.get("Mute.mp3");
            sonidoRetrocarga = assetManager.get("Mute.mp3");
            mute = assetManager.get("Mute.mp3");

        }

        //Musica de fondo
        musicFondo = Gdx.audio.newMusic(Gdx.files.internal("little-forest.mp3"));
        musicFondo.setLooping(true);
        if(estadoMusica) {
            musicFondo.play();
        }
        else{
            musicFondo.stop();
        }

        //IMPLEMENTANDO LA PAUSA
        texturaPausa = assetManager.get("Pausa.png");
        texturaPlay = assetManager.get("BtmPlay.png");
        texturaMenu = assetManager.get("back.png");
        texturaSonidoT = assetManager.get("BtmSonido.png");
        texturaMusicaT = assetManager.get("BtmMusic.png");
        texturaSonidoF = assetManager.get("BtmSonidoF.png");
        texturaMusicaF = assetManager.get("BtmMusicF.png");

        btnPantallaPausa = new Boton(texturaPausa);
        btnPantallaPausa.setAlfa(0.7f);

        btnPlay = new Boton (texturaPlay);
        btnPlay.setPosicion(Plataforma.ANCHO_CAMARA/2+150, Plataforma.ALTO_CAMARA/2);
        btnPlay.setAlfa(0.7f);

        btnMenu = new Boton (texturaMenu);
        btnMenu.setPosicion(Plataforma.ANCHO_CAMARA/2-250, Plataforma.ALTO_CAMARA/2);
        btnMenu.setAlfa(0.7f);

        if(estadoMusica) {
            btnMusicaT = new Boton(texturaMusicaT);
            btnMusicaT.setPosicion(Plataforma.ANCHO_CAMARA / 2 + 150, Plataforma.ALTO_CAMARA / 2 - 180);
            btnMusicaT.setAlfa(0.7f);

            btnMusicaF = new Boton(texturaMusicaF);
            btnMusicaF.setPosicion(Plataforma.ANCHO_CAMARA / 2 + 250, Plataforma.ALTO_CAMARA / 2 - 180);//250
            btnMusicaF.setAlfa(0.7f);
        }
        else {
            btnMusicaT = new Boton(texturaMusicaT);
            btnMusicaT.setPosicion(Plataforma.ANCHO_CAMARA / 2 + 250, Plataforma.ALTO_CAMARA / 2 - 180);
            btnMusicaT.setAlfa(0.7f);

            btnMusicaF = new Boton(texturaMusicaF);
            btnMusicaF.setPosicion(Plataforma.ANCHO_CAMARA / 2 + 150, Plataforma.ALTO_CAMARA / 2 - 180);//250
            btnMusicaF.setAlfa(0.7f);
        }


        if (estadoSonidos) {
            btnSonidoT = new Boton(texturaSonidoT);
            btnSonidoT.setPosicion(Plataforma.ANCHO_CAMARA / 2 - 250, Plataforma.ALTO_CAMARA / 2 - 180);
            btnSonidoT.setAlfa(0.7f);

            btnSonidoF = new Boton(texturaSonidoF);
            btnSonidoF.setPosicion(Plataforma.ANCHO_CAMARA / 2 - 350, Plataforma.ALTO_CAMARA / 2 - 180);
            btnSonidoF.setAlfa(0.7f);
        } else{
            btnSonidoT = new Boton(texturaSonidoT);
            btnSonidoT.setPosicion(Plataforma.ANCHO_CAMARA / 2 - 350, Plataforma.ALTO_CAMARA / 2 - 180);
            btnSonidoT.setAlfa(0.7f);

            btnSonidoF = new Boton(texturaSonidoF);
            btnSonidoF.setPosicion(Plataforma.ANCHO_CAMARA / 2 - 250, Plataforma.ALTO_CAMARA / 2 - 180);
            btnSonidoF.setAlfa(0.7f);
        }

        //Implementando la perdida
        texturaPierde = assetManager.get("GameOver.png");
        texturaMenuP = assetManager.get("back.png");
        texturaPlayP = assetManager.get("BtmPlay.png");

        btnPierde = new Boton (texturaPierde);
        btnMenuP = new Boton(texturaMenuP);
        btnMenuP.setPosicion(Plataforma.ANCHO_CAMARA-145,10);
        btnPlayP = new Boton(texturaPlayP);
        btnPlayP.setPosicion(10,10);

    }

    /*
    Dibuja TODOS los elementos del juego en la pantalla.
    Este método se está ejecutando muchas veces por segundo.
     */


    @Override
    public void render(float delta) { // delta es el tiempo entre frames (Gdx.graphics.getDeltaTime())
        if (!banderaPausa && !haPerdio) {
            if(enemD==0){
                //estadoJuego=EstadosJuego.GANO;

                //-----------Fin Nivel
                musicFondo.dispose();
                AssetManager assetManager = plataforma.getAssetManager();
                assetManager.clear();
                //-----------

                Gdx.input.setInputProcessor(null);
                siguienteNivel.clear();
                niveles.flush();
                niveles.clear();
                niveles.putString("Nivel3_A","Ya pase el nivel 1");
                niveles.flush();
                pantallaCargando = new mx.izo.xportal.PantallaCargando(plataforma);
                pantallaCargando.setNivel("Nivel3_A");
                plataforma.setScreen(pantallaCargando);
            }
            //barra vidas pregunta cuantas existen
            float barraSizeOriginal = spriteVidas.getWidth();
            float barraSizeActual = 0;
            if(vidaf<=5) {
                if (vidaf == 1) {
                    barraSizeActual = 32;
                } else if (vidaf == 2) {
                    barraSizeActual = 64;
                } else if (vidaf == 3) {
                    barraSizeActual = 96;
                } else if (vidaf == 4) {
                    barraSizeActual = 128;
                } else if (vidaf == vidafMax) {
                    barraSizeActual = 160;
                }
                //bajaBarraVidas(spriteVidas,barraSizeActual);
                //spriteVidas.setRegion(0, 0, (int) barraSizeActual, (int) spriteVidas.getHeight()); //cast importante
                spriteVidas.setSize(barraSizeActual, spriteVidas.getHeight());
            }
            if (estadoJuego != EstadosJuego.PERDIO) {
                // Actualizar objetos en la pantalla
                moverPersonaje();
                actualizarCamara(); // Mover la cámara para que siga al personaje
            }

            // Dibujar
            borrarPantalla();

            batch.setProjectionMatrix(camara.combined);

            rendererMapa.setView(camara);
            rendererMapa.render();  // Dibuja el mapa

            // Entre begin-end dibujamos nuestros objetos en pantalla
            batch.begin();
            mario.render(batch);    // Dibuja el personaje
            //ahora se dibuja alado de Score
            //spriteVidas.draw(batch);

            //dibuja barra vida

            tiempoJuego += Gdx.graphics.getDeltaTime();
            //Gdx.app.log("Tiempo juego", Float.toString(tiempoJuego));

            if (vidaf == vidafMin) {
                int scoreA = score.getInteger("theBest",0);
                if(estrellas>scoreA){
                    score.clear();
                    score.putInteger("theBest",estrellas);
                    score.flush();
                }
                haPerdio=true;
                estadoJuego = EstadosJuego.PERDIOI;
            }

            for (EnemigoV enemigoV:enemigosV){
                if (enemigoV.getVidas()>0){
                    enemigoV.render(batch);

                    if((mario.getX()>=enemigoV.getX()-rango)&&(mario.getX()<=enemigoV.getX())&&(int)tiempoJuego==tiempoDisparo&&banderaDisparo){
                        BalaV balaEnJuego = new BalaV(texturaBalaEmbudo);
                        balaEnJuego.setDireccion(-10);
                        balaEnJuego.setPosicion(enemigoV.getX(),enemigoV.getY()+50);
                        balasEnemigosV.add(balaEnJuego);
                        banderaDisparo = false;
                        tiempoJuego = 0;
                    }else if ((mario.getX()>enemigoV.getX())&&(mario.getX()<=enemigoV.getX()+rango)&&(int)tiempoJuego==tiempoDisparo&&banderaDisparo){
                        BalaV balaEnJuego = new BalaV(texturaBalaEmbudo);
                        balaEnJuego.setDireccion(-10);
                        balaEnJuego.setPosicion(enemigoV.getX()+38, enemigoV.getY() + 50);
                        balasEnemigosV.add(balaEnJuego);
                        banderaDisparo = false;
                        tiempoJuego = 0;
                    }

                    for(BalaV bala: balasEnemigosV){
                        bala.render(batch);
                        banderaDisparo = true;
                        if((bala.getX() >= mario.getX() && bala.getX()<= (mario.getX()+mario.getSprite().getWidth()))&&
                                (bala.getY() >= mario.getY() && bala.getY()<= (mario.getY()+enemigoV.getSprite().getHeight()))) {
                            int vidas = enemigoV.getVidas();
                            vidaf-=1;
                            bala.velocidadX = 10;
                            //Borrar de memoria
                            bala.setPosicion(0, 1000);
                        }
                    }

                    for(Bala bala : balas){
                        bala.setDir(1);
                        bala.render(batch,banderaDireccion);
                        //bala.render(batch,true);//en este caso solo sera una direccion, hacia arriba
                        if((bala.getX() >= enemigoV.getX() && bala.getX()<= (enemigoV.getX()+enemigoV.getSprite().getWidth()))&&
                                (bala.getY() >= enemigoV.getY() && bala.getY()<= (enemigoV.getY()+enemigoV.getSprite().getHeight()))) {
                            int vidas = enemigoV.getVidas();
                            enemigoV.setVidas(vidas-1);
                            enemD--;
                            bala.velocidadX = 10;
                            //Borrar de memoria
                            bala.setPosicion(0, 1000);
                        }
                    }
                    if(tiempoJuego>tiempoDisparo){
                        //ISAIN EL HACKER :)
                        tiempoJuego=0;
                    }
                }
                else{
                    //Borrar de memoria
                    enemigoV.setPosicion(0,2000);
                }
            }
            for(mx.izo.xportal.Enemigo enemigo:enemigos){
                if (enemigo.getVidas()>0){
                    enemigo.render(batch);

                    if((mario.getX()>=enemigo.getX()-rango)&&(mario.getX()<=enemigo.getX())&&(int)tiempoJuego==tiempoDisparo&&banderaDisparo){
                        Bala balaEnJuego = new Bala(texturaBalaPlanta);
                        balaEnJuego.setDireccion(-10);
                        balaEnJuego.setPosicion(enemigo.getX(),enemigo.getY()+50);
                        balasEnemigos.add(balaEnJuego);
                        banderaDisparo = false;
                        tiempoJuego = 0;
                    }else if ((mario.getX()>enemigo.getX())&&(mario.getX()<=enemigo.getX()+rango)&&(int)tiempoJuego==tiempoDisparo&&banderaDisparo){
                        Bala balaEnJuego = new Bala(texturaBalaPlanta);
                        balaEnJuego.setDireccion(10);
                        balaEnJuego.setPosicion(enemigo.getX(), enemigo.getY() + 50);
                        balasEnemigos.add(balaEnJuego);
                        banderaDisparo = false;
                        tiempoJuego = 0;
                    }


                    for(Bala bala: balasEnemigos){
                        bala.render(batch,banderaDireccion);
                        //banderaDisparo = true;
                        if((bala.getX() >= mario.getX() && bala.getX()<= (mario.getX()+mario.getSprite().getWidth()))&&
                                (bala.getY() >= mario.getY() && bala.getY()<= (mario.getY()+enemigo.getSprite().getHeight()))) {
                            int vidas = enemigo.getVidas();
                            vidaf-=1;
                            bala.velocidadX = 10;
                            //Borrar de memoria
                            bala.setPosicion(0, 1000);
                        }
                    }

                    for(Bala bala : balas){
                        bala.render(batch,banderaDireccion);
                        if((bala.getX() >= enemigo.getX() && bala.getX()<= (enemigo.getX()+enemigo.getSprite().getWidth()))&&
                                (bala.getY() >= enemigo.getY() && bala.getY()<= (enemigo.getY()+enemigo.getSprite().getHeight()))) {
                            int vidas = enemigo.getVidas();
                            enemigo.setVidas(vidas-1);
                            bala.velocidadX = 10;
                            //Borrar de memoria
                            bala.setPosicion(0, 1000);
                        }
                    }
                    if(tiempoJuego>tiempoDisparo){
                        tiempoJuego=0;
                    }
                }
                else{
                    //Borrar de memoria
                    enemigo.setPosicion(0,2000);
                }
            }

            //Dibuja las balas del personaje
            for (Bala bala : balas) {
                bala.render(batch, banderaDireccion);
            }


            //Elimina a enemigos de embudo
            for (int i = 0; i < enemigosV.size(); i++) {
                EnemigoV enemigoV = enemigosV.get(i);
                if (enemigoV.getY() == 2000) {
                    enemigosV.remove(i);
                }
            }
            //Elimina las balas del enemigo
            for (int i = 0; i < balasEnemigosV.size(); i++) {
                BalaV balaV = balasEnemigosV.get(i);
                if (balaV.getX() == 0) {
                    balasEnemigosV.remove(i);
                }
            }

            //Elimina las balas del personaje
            for (int i = 0; i < balas.size(); i++) {
                Bala bala = balas.get(i);
                if (bala.getY() == 1000 || bala.getY()>1000 || bala.getX() > mario.getX() + rango || mario.getX() - rango > bala.getX()) {
                    balas.remove(i);
                }
            }


            //Elimina a enemigos planta
            for (int i = 0; i < enemigos.size(); i++) {
                mx.izo.xportal.Enemigo enemigo = enemigos.get(i);
                if (enemigo.getY() == 2000) {
                    enemigos.remove(i);
                }
            }
            //Elimina las balas de planta
            for (int i = 0; i < balasEnemigos.size(); i++) {
                Bala bala = balasEnemigos.get(i);
                if (bala.getY() == 1000 || bala.getX() < 0 || bala.getX() > 4000) {
                    balasEnemigos.remove(i);
                }
            }

            batch.end();

            // Dibuja el HUD
            batch.setProjectionMatrix(camaraHUD.combined);
            batch.begin();

            // ¿Ya ganó?
            if (estadoJuego == EstadosJuego.GANO) {
                btnGana.render(batch);
            } else {
                btnIzquierda.render(batch);
                btnDerecha.render(batch);
                //btnSalto.render(batch);
                btnDisparo.render(batch);
                btnPausa.render(batch);
                // Estrellas recolectadas
                texto.mostrarMensaje(batch, "Score: " + estrellas, Plataforma.ANCHO_CAMARA - 1000, Plataforma.ALTO_CAMARA * 0.95f);
                texto.mostrarMensaje(batch, "Life: ", Plataforma.ANCHO_CAMARA - 460, Plataforma.ALTO_CAMARA * 0.95f);
                spriteVidas.setPosition(Plataforma.ANCHO_CAMARA - 400, Plataforma.ALTO_CAMARA * 0.95f - 27);
                spriteVidasF.setPosition(Plataforma.ANCHO_CAMARA - 400, Plataforma.ALTO_CAMARA * 0.95f - 27);
                spriteVidas.draw(batch);
                spriteVidasF.draw(batch);
            }
            batch.end();
        }


        else if(haPerdio){
            borrarPantalla();

            // Dibuja cuando has perdido
            batch.setProjectionMatrix(camaraHUD.combined);

            batch.begin();
            // ¿Ya ganó?
            if (estadoJuego == EstadosJuego.GANO) {
                btnGana.render(batch);
            } else {
                btnPierde.render(batch);
                btnMenuP.render(batch);
                //btnPlayP.render(batch);
            }
            batch.end();

        }

        //CUANDO ESTA EN PAUSA
        else{
            borrarPantalla();

            // Dibuja La Pausa
            batch.setProjectionMatrix(camaraHUD.combined);

            //barra vidas pregunta cuantas existen
            float barraSizeOriginal = spriteVidas.getWidth();
            float barraSizeActual = 0;
            if (vidaf == 1) {
                barraSizeActual = 32;
            } else if (vidaf == 2) {
                barraSizeActual = 64;
            } else if (vidaf == 3) {
                barraSizeActual = 96;
            } else if (vidaf == 4) {
                barraSizeActual = 128;
            } else if (vidaf == vidafMax) {
                barraSizeActual = 160;
            }
            //bajaBarraVidas(spriteVidas,barraSizeActual);
            //spriteVidas.setRegion(0, 0, (int) barraSizeActual, (int) spriteVidas.getHeight()); //cast importante
            spriteVidas.setSize(barraSizeActual, spriteVidas.getHeight());

            if (estadoJuego != EstadosJuego.PERDIO) {
                // Actualizar objetos en la pantalla
                moverPersonaje();
                actualizarCamara(); // Mover la cámara para que siga al personaje
            }
            rendererMapa.setView(camara);
            rendererMapa.render();  // Dibuja el mapa


            batch.begin();

            mario.render(batch);    // Dibuja el personaje

            for (EnemigoV enemigoV : enemigosV) {
                if (enemigoV.getVidas() > 0) {
                    enemigoV.render(batch);

                    if ((mario.getX() >= enemigoV.getX() - rango) && (mario.getX() <= enemigoV.getX()) && (int) tiempoJuego == tiempoDisparo && banderaDisparo) {
                        BalaV balaEnJuego = new BalaV(texturaBalaEmbudo);
                        balaEnJuego.setDireccion(-10);
                        balaEnJuego.setPosicion(enemigoV.getX(), enemigoV.getY() + 50);
                        balasEnemigosV.add(balaEnJuego);
                        banderaDisparo = false;
                        tiempoJuego = 0;
                    } else if ((mario.getX() > enemigoV.getX()) && (mario.getX() <= enemigoV.getX() + rango) && (int) tiempoJuego == tiempoDisparo && banderaDisparo) {
                        BalaV balaEnJuego = new BalaV(texturaBalaEmbudo);
                        balaEnJuego.setDireccion(-10);
                        balaEnJuego.setPosicion(enemigoV.getX() + 38, enemigoV.getY() + 50);
                        balasEnemigosV.add(balaEnJuego);
                        banderaDisparo = false;
                        tiempoJuego = 0;
                    }


                    if (tiempoJuego > 6) {
                        //ISAIN EL HACKER :)
                        tiempoJuego = 0;
                    }
                } else {
                    //Borrar de memoria
                    enemigoV.setPosicion(0, 2000);
                }
            }
            for (mx.izo.xportal.Enemigo enemigo : enemigos) {
                if (enemigo.getVidas() > 0) {
                    enemigo.render(batch);

                    if ((mario.getX() >= enemigo.getX() - rango) && (mario.getX() <= enemigo.getX()) && (int) tiempoJuego == tiempoDisparo && banderaDisparo) {
                        Bala balaEnJuego = new Bala(texturaBalaPlanta);
                        balaEnJuego.setDireccion(-10);
                        balaEnJuego.setPosicion(enemigo.getX(), enemigo.getY() + 50);
                        balasEnemigos.add(balaEnJuego);
                        banderaDisparo = false;
                        tiempoJuego = 0;
                        //}else if (mario.getX()>=enemigo.getX()+enemigo.getSprite().getWidth()&&mario.getX()<=enemigo.getX()+enemigo.getSprite().getWidth()+rango&&banderaDisparo&&tiempoJuego==tiempoDisparo){
                    } else if ((mario.getX() > enemigo.getX()) && (mario.getX() <= enemigo.getX() + rango) && (int) tiempoJuego == tiempoDisparo && banderaDisparo) {
                        Gdx.app.log("Deberia de disparar a la derecha", "");
                        Bala balaEnJuego = new Bala(texturaBalaPlanta);
                        balaEnJuego.setDireccion(10);
                        balaEnJuego.setPosicion(enemigo.getX() + 120, enemigo.getY() + 50);
                        balasEnemigos.add(balaEnJuego);
                        banderaDisparo = false;
                        tiempoJuego = 0;
                    }

                    if (tiempoJuego > tiempoDisparo) {
                        tiempoJuego = 0;
                    }
                } else {
                    //Borrar de memoria
                    enemigo.setPosicion(0, 2000);
                }
            }

            batch.end();
            // Dibuja el HUD
            batch.setProjectionMatrix(camaraHUD.combined);
            batch.begin();

            // ¿Ya ganó?
            if (estadoJuego == EstadosJuego.GANO) {
                btnGana.render(batch);
            } else {
                // Estrellas recolectadas
                texto.mostrarMensaje(batch, "Score: " + estrellas, Plataforma.ANCHO_CAMARA - 1000, Plataforma.ALTO_CAMARA * 0.95f);
                texto.mostrarMensaje(batch, "Life: ", Plataforma.ANCHO_CAMARA - 460, Plataforma.ALTO_CAMARA * 0.95f);
                spriteVidas.setPosition(Plataforma.ANCHO_CAMARA - 400, Plataforma.ALTO_CAMARA * 0.95f - 27);
                spriteVidasF.setPosition(Plataforma.ANCHO_CAMARA - 400, Plataforma.ALTO_CAMARA * 0.95f - 27);
                spriteVidas.draw(batch);
                spriteVidasF.draw(batch);
            }
            batch.end();


            batch.begin();
            // ¿Ya ganó?
            if (estadoJuego == EstadosJuego.GANO) {
                btnGana.render(batch);
            } else {
                btnPantallaPausa.render(batch);
                btnPantallaPausa.setAlfa(1);
                btnPlay.render(batch);
                btnPlay.setAlfa(0.9f);
                btnMenu.render(batch);
                btnMenu.setAlfa(0.9f);
                if(estadoMusica) {
                    btnMusicaT.setPosicion(Plataforma.ANCHO_CAMARA / 2 + 150, Plataforma.ALTO_CAMARA / 2 - 180);
                    btnMusicaT.setAlfa(0.9f);
                    btnMusicaT.render(batch);
                    btnMusicaF.setPosicion(Plataforma.ANCHO_CAMARA / 2 + 250, Plataforma.ALTO_CAMARA / 2 - 180);
                    btnMusicaF.setAlfa(0.9f);

                }else {
                    btnMusicaT.setPosicion(Plataforma.ANCHO_CAMARA / 2 + 250, Plataforma.ALTO_CAMARA / 2 - 180);
                    btnMusicaT.setAlfa(0.9f);
                    btnMusicaF.setPosicion(Plataforma.ANCHO_CAMARA / 2 + 150, Plataforma.ALTO_CAMARA / 2 - 180);
                    btnMusicaF.render(batch);
                    btnMusicaF.setAlfa(0.9f);
                }
                if(estadoSonidos) {
                    btnSonidoT.setPosicion(Plataforma.ANCHO_CAMARA / 2 - 250, Plataforma.ALTO_CAMARA / 2 - 180);
                    btnSonidoT.setAlfa(0.9f);
                    btnSonidoT.render(batch);
                    btnSonidoF.setPosicion(Plataforma.ANCHO_CAMARA / 2 - 350, Plataforma.ALTO_CAMARA / 2 - 180);
                    btnSonidoF.setAlfa(0.9f);
                }else{
                    btnSonidoT.setPosicion(Plataforma.ANCHO_CAMARA / 2 - 350, Plataforma.ALTO_CAMARA / 2 - 180);
                    btnSonidoT.setAlfa(0.9f);
                    btnSonidoF.setPosicion(Plataforma.ANCHO_CAMARA / 2 - 250, Plataforma.ALTO_CAMARA / 2 - 180);
                    btnSonidoF.render(batch);
                    btnSonidoF.setAlfa(0.9f);
                }
            }
            batch.end();
        }
    }

    // Actualiza la posición de la cámara para que el personaje esté en el centro,
    // excepto cuando está en la primera y última parte del mundo
    private void actualizarCamara() {
        float posX = mario.getX();
        float posY = mario.getY();
        // Si está en la parte 'media'
        if (posX >= Plataforma.ANCHO_CAMARA / 2 && posX <= ANCHO_MAPA - Plataforma.ANCHO_CAMARA / 2) {
            // El personaje define el centro de la cámara
            camara.position.set((int) posX, camara.position.y, 0);
        } else if (posX > ANCHO_MAPA - Plataforma.ANCHO_CAMARA / 2) {    // Si está en la última mitad
            // La cámara se queda a media pantalla antes del fin del mundo  :)
            camara.position.set(ANCHO_MAPA - Plataforma.ANCHO_CAMARA / 2, camara.position.y, 0);
        }//Si el personaje se coloca en el centro de la camara

        if ((posY >= Plataforma.ALTO_CAMARA / 2 && posY <= ALTO_MAPA - Plataforma.ALTO_CAMARA / 2)) {
            // El personaje define el centro de la cámara
            camara.position.set(camara.position.x, (int) posY, 0);
        } else if ((posY > ALTO_MAPA - Plataforma.ALTO_CAMARA / 2)) {    // Si está en la última mitad
            // La cámara se queda a media pantalla antes del fin del mundo  :)
            camara.position.set(camara.position.x, ALTO_MAPA - Plataforma.ALTO_CAMARA / 2, 0);
        }
        camara.update();
    }

    private void moverPersonaje() {
        // Prueba caída libre inicial o movimiento horizontal
        switch (mario.getEstadoMovimiento()) {
            case INICIANDO:     // Mueve el personaje en Y hasta que se encuentre sobre un bloque
                // Los bloques en el mapa son de 16x16
                // Calcula la celda donde estaría después de moverlo
                int celdaX = (int)(mario.getX()/ TAM_CELDA);
                int celdaY = (int)((mario.getY()+mario.VELOCIDAD_Y)/ TAM_CELDA);
                // Recuperamos la celda en esta posición
                // La capa 0 es el fondo
                TiledMapTileLayer capa = (TiledMapTileLayer)mapa.getLayers().get(1);
                TiledMapTileLayer.Cell celda = capa.getCell(celdaX, celdaY);
                // probar si la celda está ocupada
                if (celda==null) {
                    // Celda vacía, entonces el personaje puede avanzar
                    mario.caer();
                }  else if ( !esCoin(celda) ) {  // Las estrellas no lo detienen :)
                    // Dejarlo sobre la celda que lo detiene
                    mario.setPosicion(mario.getX(), (celdaY + 1) * TAM_CELDA);
                    mario.setEstadoMovimiento(mx.izo.xportal.Personaje.EstadoMovimiento.QUIETO);
                }
                else if( !esVida(celda) ) {  // Las estrellas no lo detienen :)
                    // Dejarlo sobre la celda que lo detiene
                    mario.setPosicion(mario.getX(), (celdaY + 1) * TAM_CELDA);
                    mario.setEstadoMovimiento(mx.izo.xportal.Personaje.EstadoMovimiento.QUIETO);
                }

                break;
            case MOV_DERECHA:       // Se mueve horizontal
            case MOV_IZQUIERDA:
                probarChoqueParedes();      // Prueba si debe moverse
                break;
        }
        // Prueba si debe caer por llegar a un espacio vacío
        if ( mario.getEstadoMovimiento()!= mx.izo.xportal.Personaje.EstadoMovimiento.INICIANDO
                && (mario.getEstadoSalto() != mx.izo.xportal.Personaje.EstadoSalto.SUBIENDO) ) {
            // Calcula la celda donde estaría después de moverlo
            int celdaX = (int) (mario.getX() / TAM_CELDA);
            int celdaY = (int) ((mario.getY() + mario.VELOCIDAD_Y) / TAM_CELDA);
            // Recuperamos la celda en esta posición
            // La capa 0 es el fondo
            TiledMapTileLayer capa = (TiledMapTileLayer) mapa.getLayers().get(1);
            TiledMapTileLayer.Cell celdaAbajo = capa.getCell(celdaX, celdaY);
            TiledMapTileLayer.Cell celdaDerecha = capa.getCell(celdaX+1, celdaY);
            // probar si la celda está ocupada
            if ( (celdaAbajo==null && celdaDerecha==null) || esCoin(celdaAbajo) || esCoin(celdaDerecha) ) {
                // Celda vacía, entonces el personaje puede avanzar
                mario.caer();
                mario.setEstadoSalto(mx.izo.xportal.Personaje.EstadoSalto.CAIDA_LIBRE);
            }
            else if( (celdaAbajo==null && celdaDerecha==null) || esVida(celdaAbajo) || esVida(celdaDerecha) ) {
                // Celda vacía, entonces el personaje puede avanzar
                mario.caer();
                mario.setEstadoSalto(mx.izo.xportal.Personaje.EstadoSalto.CAIDA_LIBRE);
            }
            else {
                // Dejarlo sobre la celda que lo detiene
                mario.setPosicion(mario.getX(), (celdaY + 1) * TAM_CELDA);
                mario.setEstadoSalto(mx.izo.xportal.Personaje.EstadoSalto.EN_PISO);
            }
        }

        // Saltar
        switch (mario.getEstadoSalto()) {
            case SUBIENDO:
            case BAJANDO:
                mario.actualizarSalto(mapa);    // Actualizar posición en 'y'
                break;
        }
    }

    // Prueba si puede moverse a la izquierda o derecha
    private void probarChoqueParedes() {
        mx.izo.xportal.Personaje.EstadoMovimiento estado = mario.getEstadoMovimiento();
        // Quitar porque este método sólo se llama cuando se está moviendo
        if ( estado!= mx.izo.xportal.Personaje.EstadoMovimiento.MOV_DERECHA && estado!= mx.izo.xportal.Personaje.EstadoMovimiento.MOV_IZQUIERDA){
            return;
        }
        float px = mario.getX();    // Posición actual
        // Posición después de actualizar
        px = mario.getEstadoMovimiento()== mx.izo.xportal.Personaje.EstadoMovimiento.MOV_DERECHA? px+mario.getVelocidadPersonaje():
                px-mario.getVelocidadPersonaje();
        int celdaX = (int)(px/TAM_CELDA);   // Casilla del personaje en X
        if (mario.getEstadoMovimiento()== mx.izo.xportal.Personaje.EstadoMovimiento.MOV_DERECHA) {
            celdaX++;   // Casilla del lado derecho
        }
        int celdaY = (int)(mario.getY()/TAM_CELDA); // Casilla del personaje en Y
        TiledMapTileLayer capaPlataforma = (TiledMapTileLayer) mapa.getLayers().get(1);
        TiledMapTileLayer capaPlataforma1 = (TiledMapTileLayer) mapa.getLayers().get(4);
        TiledMapTileLayer capaPlataforma2 = (TiledMapTileLayer) mapa.getLayers().get(6);
        //******************************
        if ( capaPlataforma.getCell(celdaX,celdaY) != null || capaPlataforma.getCell(celdaX,celdaY+1) != null ) {
            // Colisionará, dejamos de moverlo
            if ( esCoin(capaPlataforma.getCell(celdaX,celdaY))) {
                // Borrar esta estrella y contabilizar
                capaPlataforma.setCell(celdaX,celdaY,null);
                estrellas++;
                sonidoEstrella.play();
            } else if (esCoin(capaPlataforma.getCell(celdaX,celdaY+1)) ) {
                // Borrar esta estrella y contabilizar
                capaPlataforma.setCell(celdaX,celdaY+1,null);
                estrellas++;
                sonidoEstrella.play();
            } else if ( esVida(capaPlataforma.getCell(celdaX,celdaY)) ) {
                // Borrar esta estrella y contabilizar
                capaPlataforma.setCell(celdaX,celdaY,null);
                if(vidaf<=vidafMax){
                    vidaf++;
                }
                sonidoVida.play();
            }
            else if (esVida(capaPlataforma.getCell(celdaX,celdaY+1)) ) {
                // Borrar esta estrella y contabilizar
                capaPlataforma.setCell(celdaX,celdaY+1,null);
                vidaf++;
                sonidoVida.play();
            }else if (esLlave1(capaPlataforma.getCell(celdaX,celdaY))){
                eliminarLlave1();
                estrellas++;
                abrirPuerta1();
                sonidoLlave.play();

            }else if (esLlave2(capaPlataforma.getCell(celdaX,celdaY))){
                eliminarLlave2();
                estrellas++;
                abrirPuerta2();
                sonidoLlave.play();

            }else if(esVida(capaPlataforma.getCell(celdaX,celdaY))){
                capaPlataforma.setCell(celdaX,celdaY+1,null);
                vidaf++;
                sonidoEstrella.play();

            }else if(esPistola(capaPlataforma.getCell(celdaX,celdaY))){
                eliminarPistolita();
                banderaArma = true;
                sonidoRetrocarga.play();
            }
            else {
                mario.setEstadoMovimiento(mx.izo.xportal.Personaje.EstadoMovimiento.QUIETO);
            }
        }

        if ( capaPlataforma1.getCell(celdaX,celdaY) != null || capaPlataforma1.getCell(celdaX,celdaY+1) != null ) {
            if ( esPuertaA( capaPlataforma1.getCell(celdaX,celdaY) ) && llaveA ) {
                sonidoPierde.play();
                estadoJuego = EstadosJuego.PERDIO;
                //dispose();
                Timer.schedule(new Timer.Task() {
                    @Override
                    public void run() {
                        Gdx.input.setInputProcessor(null);
                        musicFondo.dispose();
                        AssetManager assetManager = plataforma.getAssetManager();
                        assetManager.clear();
                        //Actualizar preferencias
                        int scoreA = score.getInteger("theBest",0);
                        if(estrellas>scoreA){
                            score.clear();
                            score.putInteger("theBest",estrellas);
                            score.flush();
                        }
                        Gdx.app.log("Ya entre ","al nivel miniGame");
                        siguienteNivel.clear();
                        siguienteNivel.putString("Nivel2_A","Entre al nivel 2A");
                        siguienteNivel.flush();
                        niveles.clear();
                        niveles.putString("MiniGame1","Ya pase el nivel 1");
                        niveles.flush();
                        pantallaCargando = new mx.izo.xportal.PantallaCargando(plataforma);
                        pantallaCargando.setNivel("MiniGame1");
                        plataforma.setScreen(pantallaCargando);
                    }
                }, 1);  // 3 segundos
            }
        }
        if ( capaPlataforma2.getCell(celdaX,celdaY) != null || capaPlataforma2.getCell(celdaX,celdaY+1) != null ) {
            if ( esPuertaA2( capaPlataforma2.getCell(celdaX,celdaY) ) && llaveB) {
                Gdx.input.setInputProcessor(null);
                sonidoPierde.play();
                estadoJuego = EstadosJuego.PERDIO;
                //dispose();
                Timer.schedule(new Timer.Task() {
                    @Override
                    public void run() {
                        musicFondo.dispose();
                        AssetManager assetManager = plataforma.getAssetManager();
                        assetManager.clear();
                        //Actualizar preferencias
                        int scoreA = score.getInteger("theBest",0);
                        if(estrellas>scoreA){
                            score.clear();
                            score.putInteger("theBest",estrellas);
                            score.flush();
                        }
                        siguienteNivel.clear();
                        siguienteNivel.putString("Nivel2_B","Entre al nivel 2B");
                        siguienteNivel.flush();
                        niveles.clear();
                        niveles.putString("MiniGame1","Ya pase el nivel 1");
                        niveles.flush();
                        pantallaCargando = new mx.izo.xportal.PantallaCargando(plataforma);
                        pantallaCargando.setNivel("MiniGame1");
                        plataforma.setScreen(pantallaCargando);
                    }
                }, 1);  // 3 segundos
            }
        }
        mario.actualizar();
    }

    private void abrirPuerta1() {
        TiledMapTileLayer capaPlataforma = (TiledMapTileLayer) mapa.getLayers().get(3);
        capaPlataforma.setVisible(false);
        capaPlataforma = (TiledMapTileLayer) mapa.getLayers().get(4);
        capaPlataforma.setVisible(true);
    }

    private void abrirPuerta2() {
        TiledMapTileLayer capaPlataforma = (TiledMapTileLayer) mapa.getLayers().get(5);
        capaPlataforma.setVisible(false);
        capaPlataforma = (TiledMapTileLayer) mapa.getLayers().get(6);
        capaPlataforma.setVisible(true);
    }

    private void eliminarLlave2() {
        TiledMapTileLayer capaPlataforma = (TiledMapTileLayer) mapa.getLayers().get(1);
        capaPlataforma.setCell(68,23,null);
        capaPlataforma.setCell(68,24,null);
        capaPlataforma.setCell(68,25,null);
        capaPlataforma.setCell(68,26,null);
        capaPlataforma.setCell(68,27,null);
        capaPlataforma.setCell(69,23,null);
        capaPlataforma.setCell(69,24,null);
        capaPlataforma.setCell(69,25,null);
        capaPlataforma.setCell(69,26,null);
        capaPlataforma.setCell(69,27,null);
        capaPlataforma.setCell(70,23,null);
        capaPlataforma.setCell(70,24,null);
        capaPlataforma.setCell(70,25,null);
        capaPlataforma.setCell(70,26,null);
        capaPlataforma.setCell(70,27,null);
        llaveB = true;

    }

    private void eliminarLlave1() {
        TiledMapTileLayer capaPlataforma = (TiledMapTileLayer) mapa.getLayers().get(1);
        capaPlataforma.setCell(38,22,null);
        capaPlataforma.setCell(38,21,null);
        capaPlataforma.setCell(38,20,null);
        capaPlataforma.setCell(38,19,null);
        capaPlataforma.setCell(38,18,null);
        capaPlataforma.setCell(39,22,null);
        capaPlataforma.setCell(39,21,null);
        capaPlataforma.setCell(39,20,null);
        capaPlataforma.setCell(39,19,null);
        capaPlataforma.setCell(39,18,null);
        capaPlataforma.setCell(40,22,null);
        capaPlataforma.setCell(40,21,null);
        capaPlataforma.setCell(40,20,null);
        capaPlataforma.setCell(40,19,null);
        capaPlataforma.setCell(40,18,null);
        llaveA = true;
    }
    private void eliminarPistolita() {
        TiledMapTileLayer capaPlataforma = (TiledMapTileLayer) mapa.getLayers().get(1);
        capaPlataforma.setCell(8,12,null);
        capaPlataforma.setCell(8,11,null);
        capaPlataforma.setCell(9,12,null);
        capaPlataforma.setCell(9,11,null);
    }



    // Verifica si esta casilla tiene una estrella (simplificar con la anterior)
    private boolean esCoin(TiledMapTileLayer.Cell celda) {
        if (celda==null) {
            return false;
        }
        Object propiedad = celda.getTile().getProperties().get("tipo");
        return "coin".equals(propiedad);
    }

    private boolean esVida(TiledMapTileLayer.Cell celda){
        if(celda==null)
            return false;
        Object propiedad =celda.getTile().getProperties().get("tipo");
        return "pildora".equals(propiedad);
    }
    // Verifica si esta casilla tiene una llave (simplificar con la anterior)
    private boolean esLlave1(TiledMapTileLayer.Cell celda) {
        if (celda==null) {
            return false;
        }
        Object propiedad = celda.getTile().getProperties().get("tipo");
        return "llave1".equals(propiedad);
    }

    private boolean esLlave2(TiledMapTileLayer.Cell celda) {
        if (celda==null) {
            return false;
        }
        Object propiedad = celda.getTile().getProperties().get("tipo");
        return "llave2".equals(propiedad);
    }

    // Verifica si esta casilla tiene un hongo (simplificar con las anteriores)
    private boolean esPuertaA(TiledMapTileLayer.Cell celda) {
        if (celda==null) {
            return false;
        }
        Object propiedad = celda.getTile().getProperties().get("tipo");
        return "puertaA".equals(propiedad);
    }

    private boolean esPuertaA2(TiledMapTileLayer.Cell celda){
        if (celda==null) {
            return false;
        }
        Object propiedad =celda.getTile().getProperties().get("tipo");
        return "puertaA2".equals(propiedad);

    }

    private boolean esPistola(TiledMapTileLayer.Cell celda){
        if (celda==null) {
            return false;
        }
        Object propiedad =celda.getTile().getProperties().get("tipo");
        return "pistolita".equals(propiedad);

    }

    private void borrarPantalla() {
        Gdx.gl.glClearColor(0.42f, 0.55f, 1, 1);    // r, g, b, alpha
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }

    @Override
    public void resize(int width, int height) {
        vista.update(width,height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {
        //dispose();
    }

    // Libera los assets
    @Override
    public void dispose() {
        // Los assets se liberan a través del assetManager
        AssetManager assetManager = plataforma.getAssetManager();
        // Carga los recursos de la siguiente pantalla (PantallaJuego)
        assetManager.unload("inv.tmx");  // Cargar info del mapa
        //assetManager.load("Mapa.tmx", TiledMap.class);
        assetManager.unload("nave.png");
        assetManager.unload("salto.png");


        // Cargar imagen
        // Texturas de los botones
        assetManager.unload("BtmDerecho.png");
        assetManager.unload("BtmIzquierdo.png");
        assetManager.unload("BtmSaltar.png");
        assetManager.unload("shoot.png");
        assetManager.unload("bala.png");
        assetManager.unload("embudo.png");
        //assetManager.load("alien1.png",Texture.class);
        assetManager.unload("Planta.png");
        assetManager.unload("balaPlanta.png");
        assetManager.unload("balaA.png");
        assetManager.unload("BtmPausa.png");

        //aliens
        assetManager.unload("alien1.png");
        //assetManager.unload("alien2.png");
        //assetManager.unload("alien3.png");

        //cargar barra
        assetManager.unload("barra.png");
        assetManager.unload("barraF.png");


        //Para la pausa
        assetManager.unload("Pausa.png");
        assetManager.unload("BtmPlay.png");
        assetManager.unload("back.png");
        assetManager.unload("BtmSonido.png");
        assetManager.unload("BtmMusic.png");
        assetManager.unload("BtmSonidoF.png");
        assetManager.unload("BtmMusicF.png");

        //Para cuando pierde
        assetManager.unload("GameOver.png");


        // Fin del juego
        assetManager.unload("ganaste.png");
        // Efecto al tomar la moneda
        assetManager.unload("monedas.mp3");
        assetManager.unload("llave.mp3");
        assetManager.unload("opendoor.mp3");
        assetManager.unload("vidawi.mp3");

    }


    /*
    Clase utilizada para manejar los eventos de touch en la pantalla
     */
    public class ProcesadorEntrada extends InputAdapter
    {
        private Vector3 coordenadas = new Vector3();
        private float x, y;     // Las coordenadas en la pantalla

        /*
        Se ejecuta cuando el usuario PONE un dedo sobre la pantalla, los dos primeros parámetros
        son las coordenadas relativas a la pantalla física (0,0) en la esquina superior izquierda
        pointer - es el número de dedo que se pone en la pantalla, el primero es 0
        button - el botón del mouse
         */

        @Override
        public boolean keyDown(int keycode) {
            if (keycode== Input.Keys.BACK) {
                if(!(estadoJuego == EstadosJuego.PAUSADO)) {
                    estadoJuego = EstadosJuego.PAUSADO;
                    banderaPausa = true;
                }
                else{
                    estadoJuego = EstadosJuego.JUGANDO;
                    banderaPausa = false;
                }
            }
            return true; // Para que el sistema operativo no la procese
        }

        @Override
        public boolean touchDown(int screenX, int screenY, int pointer, int button) {
            transformarCoordenadas(screenX, screenY);
            if (estadoJuego==EstadosJuego.JUGANDO) {
                // Preguntar si las coordenadas están sobre el botón derecho
                if (btnDerecha.contiene(x, y) && mario.getEstadoMovimiento() != mx.izo.xportal.Personaje.EstadoMovimiento.INICIANDO) {
                    // Tocó el botón derecha, hacer que el personaje se mueva a la derecha
                    banderaDireccion = false;
                    //mario.setBanderaPosicion(banderaDireccion);
                    mario.setEstadoMovimiento(mx.izo.xportal.Personaje.EstadoMovimiento.MOV_DERECHA);

                } else if (btnIzquierda.contiene(x, y) && mario.getEstadoMovimiento() != mx.izo.xportal.Personaje.EstadoMovimiento.INICIANDO) {
                    // Tocó el botón izquierda, hacer que el personaje se mueva a la izquierda
                    banderaDireccion = true;
                    //mario.setBanderaPosicion(banderaDireccion);
                    mario.setEstadoMovimiento(mx.izo.xportal.Personaje.EstadoMovimiento.MOV_IZQUIERDA);

                }else if (btnDisparo.contiene(x, y)) {
                    // Tocó el botón disparar
                    sonidoPistola.play();
                    Bala bala = new Bala(texturaBala);
                    bala.setVelocidadX(5);
                    bala.setPosicion(mario.getX(),mario.getY()+30);
                    if(banderaDireccion){
                        bala.setDireccion(10);
                        balas.add(bala);
                    }else {
                        bala.setDireccion(10);
                        balas.add(bala);
                    }
                }else if(btnPausa.contiene(x,y)){
                    //plataforma.setScreen(new PantallaPausa(plataforma));
                    estadoJuego=EstadosJuego.PAUSADO;
                    banderaPausa = true;
                }
            } else if (estadoJuego==EstadosJuego.GANO) {
                if (btnGana.contiene(x,y)) {
                    Gdx.app.exit();//Buuu
                }
            } else if (estadoJuego == EstadosJuego.PAUSADO){
                if (btnPlay.contiene(x, y)) {
                    // Tocó el botón derecha, hacer que el personaje se mueva a la derecha
                    banderaPausa = false;
                    estadoJuego=EstadosJuego.JUGANDO;
                }else if(btnMenu.contiene(x,y)){
                    Gdx.input.setInputProcessor(null);
                    musicFondo.dispose();
                    dispose();
                    plataforma.setScreen(new mx.izo.xportal.Menu(plataforma));

                } else if(btnSonidoT.contiene(x,y)){
                    AssetManager assetManager = plataforma.getAssetManager();
                    estadoSonidos = false;
                    sonidos.clear();
                    sonidos.putBoolean("estadoSonidos",false);
                    sonidos.flush();
                    sonidoEstrella = assetManager.get("Mute.mp3");
                    sonidoRetrocarga = assetManager.get("Mute.mp3");
                    sonidoLlave = assetManager.get("Mute.mp3");
                    sonidoPierde = assetManager.get("Mute.mp3");
                    sonidoPistola = assetManager.get("Mute.mp3");
                    sonidoVida = assetManager.get("Mute.mp3");
                }
                else if(btnSonidoF.contiene(x,y)){
                    AssetManager assetManager = plataforma.getAssetManager();

                    estadoSonidos = true;
                    sonidos.clear();
                    sonidos.putBoolean("estadoSonidos",true);
                    sonidos.flush();
                    sonidoEstrella = assetManager.get("monedas.mp3");
                    sonidoPierde = assetManager.get("opendoor.mp3");
                    sonidoVida= assetManager.get("vidawi.mp3");
                    sonidoLlave=assetManager.get("llave.mp3");
                    sonidoPistola=assetManager.get("pistola.mp3");
                    sonidoRetrocarga = assetManager.get("retrocarga.wav");
                }

                if(btnMusicaT.contiene(x,y)){
                    estadoMusica=false;
                    musica.clear();
                    musica.putBoolean("estadoMusica",false);
                    musica.flush();
                    musicFondo.pause();
                }
                else if(btnMusicaF.contiene(x,y)){
                    Gdx.app.log("Tocando"," musica apagada");
                    estadoMusica = true;
                    musica.clear();
                    musica.putBoolean("estadoMusica",true);
                    musica.flush();
                    musicFondo.play();
                }
            }else if(estadoJuego == EstadosJuego.PERDIOI || haPerdio){
                if(btnMenuP.contiene(x,y)){
                    Gdx.input.setInputProcessor(null);
                    musicFondo.dispose();
                    dispose();
                    plataforma.setScreen(new mx.izo.xportal.Menu(plataforma));
                }
                else if(btnPlayP.contiene(x,y)){
                    Gdx.input.setInputProcessor(null);
                    musicFondo.dispose();
                    plataforma.setScreen(new PantallaJuego(plataforma));
                }
            }
            return true;    // Indica que ya procesó el evento
        }

        /*
        Se ejecuta cuando el usuario QUITA el dedo de la pantalla.
         */
        @Override
        public boolean touchUp(int screenX, int screenY, int pointer, int button) {
            transformarCoordenadas(screenX, screenY);
            // Preguntar si las coordenadas son de algún botón para DETENER el movimiento
            if ( mario.getEstadoMovimiento()!= mx.izo.xportal.Personaje.EstadoMovimiento.INICIANDO && (btnDerecha.contiene(x, y) || btnIzquierda.contiene(x,y)) ) {
                // Tocó el botón derecha, hacer que el personaje se mueva a la derecha
                mario.setEstadoMovimiento(mx.izo.xportal.Personaje.EstadoMovimiento.QUIETO);
            }
            return true;    // Indica que ya procesó el evento
        }


        // Se ejecuta cuando el usuario MUEVE el dedo sobre la pantalla
        @Override
        public boolean touchDragged(int screenX, int screenY, int pointer) {
            transformarCoordenadas(screenX, screenY);
            // Acaba de salir de las fechas (y no es el botón de salto)
            if (x<Plataforma.ANCHO_CAMARA/2 && mario.getEstadoMovimiento()!= mx.izo.xportal.Personaje.EstadoMovimiento.QUIETO) {
                if (!btnIzquierda.contiene(x, y) && !btnDerecha.contiene(x, y) ) {
                    mario.setEstadoMovimiento(mx.izo.xportal.Personaje.EstadoMovimiento.QUIETO);
                }
            }
            return true;
        }


        private void transformarCoordenadas(int screenX, int screenY) {
            // Transformar las coordenadas de la pantalla física a la cámara HUD
            coordenadas.set(screenX, screenY, 0);
            camaraHUD.unproject(coordenadas);
            // Obtiene las coordenadas relativas a la pantalla virtual
            x = coordenadas.x;
            y = coordenadas.y;
        }
    }


    public enum EstadosJuego {
        GANO,
        JUGANDO,
        PAUSADO,
        PERDIO,
        PERDIOI
    }

}