package mx.izo.xportal;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * Created by isain on 10/11/2016.
 */
public class Menu implements Screen
{
    // Referencia al objeto de tipo Game (tiene setScreen para cambiar de pantalla)
    private Plataforma plataforma;

    // La cámara y vista principal
    private OrthographicCamera camara;
    private Viewport vista;

    // Objeto para dibujar en la pantalla
    private SpriteBatch batch;

    // Fondo
    private Texture texturaMenu;

    // Opciones
    private Texture texturaPlay;
    private Texture texturaAbout;
    private Texture texturaSettings;
    private Texture texturaScore;
    private Texture texturaExit;

    private Boton btnPlay;
    private Boton btnAbout;
    private Boton btnSettings;
    private Boton btnScore;
    private Boton btnExit;

    public Menu(Plataforma plataforma) {
        this.plataforma = plataforma;
    }

    /*
    Se ejecuta al mostrar este Screen como pantalla de la app
     */
    @Override
    public void show() {
        // Crea la cámara/vista
        camara = new OrthographicCamera(Plataforma.ANCHO_CAMARA, Plataforma.ALTO_CAMARA);
        camara.position.set(Plataforma.ANCHO_CAMARA / 2, Plataforma.ALTO_CAMARA / 2, 0);
        camara.update();
        vista = new StretchViewport(Plataforma.ANCHO_CAMARA, Plataforma.ALTO_CAMARA, camara);

        batch = new SpriteBatch();

        cargarRecursos();
        crearObjetos();

        Gdx.input.setInputProcessor(new ProcesadorEntrada());
    }

    // Carga los recursos a través del administrador de assets
    private void cargarRecursos() {
        // Cargar las texturas/mapas
        AssetManager assetManager = plataforma.getAssetManager();   // Referencia al assetManager

        assetManager.load("MenuDef.png", Texture.class);    // Cargar imagen

        // Texturas de los botones
        assetManager.load("BtmAbout.png", Texture.class);
        assetManager.load("BtmPlay.png", Texture.class);
        assetManager.load("BtmPremios.png",Texture.class);
        assetManager.load("BtmSettings.png",Texture.class);
        assetManager.load("BtmExit.png",Texture.class);


        // Se bloquea hasta que cargue todos los recursos
        assetManager.finishLoading();
    }

    private void crearObjetos() {
        AssetManager assetManager = plataforma.getAssetManager();   // Referencia al assetManager
        // Carga el mapa en memoria
        texturaMenu = assetManager.get("MenuDef.png");

        texturaPlay = assetManager.get("BtmPlay.png");
        texturaAbout = assetManager.get("BtmAbout.png");
        texturaSettings = assetManager.get("BtmSettings.png");
        texturaScore = assetManager.get("BtmPremios.png");
        texturaExit = assetManager.get("BtmExit.png");

        btnAbout = new Boton(texturaAbout);
        btnAbout.setPosicion(Plataforma.ANCHO_CAMARA/4 - texturaAbout.getWidth()/2+703,
                Plataforma.ALTO_CAMARA/2 - texturaAbout.getHeight()/2-55);

        btnPlay = new Boton(texturaPlay);
        btnPlay.setPosicion(3*Plataforma.ANCHO_CAMARA/4 - texturaPlay.getWidth()/2-305,
                Plataforma.ALTO_CAMARA/2 - texturaPlay.getHeight()/2-58);

        btnSettings = new Boton(texturaSettings);
        btnSettings.setPosicion(3*Plataforma.ANCHO_CAMARA/4 - texturaPlay.getWidth()/2-203,
                Plataforma.ALTO_CAMARA/2 - texturaPlay.getHeight()/2-157);

        btnScore = new Boton(texturaScore);
        btnScore.setPosicion(3*Plataforma.ANCHO_CAMARA/4 - texturaPlay.getWidth()/2-44,
                Plataforma.ALTO_CAMARA/2 - texturaPlay.getHeight()/2-157);

        btnExit = new Boton(texturaExit);
        btnExit.setPosicion(20,10);
    }

    /*
    Dibuja TODOS los elementos del juego en la pantalla.
    Este método se está ejecutando muchas veces por segundo.
     */
    @Override
    public void render(float delta) { // delta es el tiempo entre frames (Gdx.graphics.getDeltaTime())

        // Dibujar
        borrarPantalla();

        batch.setProjectionMatrix(camara.combined);

        // Entre begin-end dibujamos nuestros objetos en pantalla
        batch.begin();

        batch.draw(texturaMenu, 0, 0);
        btnAbout.render(batch);
        btnPlay.render(batch);
        btnScore.render(batch);
        btnSettings.render(batch);
        btnExit.render(batch);
        batch.end();
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

    }

    // Libera los assets
    @Override
    public void dispose() {
        // Los assets se liberan a través del assetManager
        AssetManager assetManager = plataforma.getAssetManager();
        assetManager.unload("MenuDef.png");
        assetManager.unload("BtmPlay.png");
        assetManager.unload("BtmAbout.png");

        assetManager.unload("BtmSettings.png");
        assetManager.unload("BtmPremios.png");
        assetManager.unload("BtmExit.png");

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
        public boolean touchDown(int screenX, int screenY, int pointer, int button) {
            transformarCoordenadas(screenX, screenY);

            return true;    // Indica que ya procesó el evento
        }

        /*
        Se ejecuta cuando el usuario QUITA el dedo de la pantalla.
         */
        @Override
        public boolean touchUp(int screenX, int screenY, int pointer, int button) {
            transformarCoordenadas(screenX, screenY);

            if (btnPlay.contiene(x,y)){
                plataforma.setScreen(new PantallaCargando(plataforma));
            } else if (btnAbout.contiene(x,y)){
                plataforma.setScreen(new AcercaDe(plataforma));
            } else if(btnScore.contiene(x,y)){
                plataforma.setScreen(new PScore(plataforma));
            } else if (btnSettings.contiene(x,y)){
                plataforma.setScreen(new PSettings(plataforma));
            }else if (btnExit.contiene(x,y)){
                System.exit(0);
                //Un test para el minigame
                //plataforma.setScreen(new CargandoMiniGame1(plataforma));
            }
            return true;    // Indica que ya procesó el evento
        }


        // Se ejecuta cuando el usuario MUEVE el dedo sobre la pantalla
        @Override
        public boolean touchDragged(int screenX, int screenY, int pointer) {
            transformarCoordenadas(screenX, screenY);

            return true;
        }


        private void transformarCoordenadas(int screenX, int screenY) {
            // Transformar las coordenadas de la pantalla física a la cámara HUD
            coordenadas.set(screenX, screenY, 0);
            camara.unproject(coordenadas);
            // Obtiene las coordenadas relativas a la pantalla virtual
            x = coordenadas.x;
            y = coordenadas.y;
        }
    }

}
