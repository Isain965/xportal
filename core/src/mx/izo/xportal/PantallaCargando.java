package mx.izo.xportal;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * Created by isain on 10/11/2016.
 */
public class PantallaCargando implements Screen {
    private String nivel = "";

    private Plataforma plataforma;

    // La cámara y vista principal
    private OrthographicCamera camara;
    private Viewport vista;
    private SpriteBatch batch;

    // Imagen cargando
    private Texture texturaCargando;
    private Sprite spriteCargando;

    private  Texture texturaFondo;

    private AssetManager assetManager;  // Asset manager principal

    public PantallaCargando(Plataforma plataforma) {
        this.plataforma = plataforma;
        this.assetManager = plataforma.getAssetManager();
    }

    @Override
    public void show() {
        // Crea la cámara/vista
        camara = new OrthographicCamera(Plataforma.ANCHO_CAMARA, Plataforma.ALTO_CAMARA);
        camara.position.set(Plataforma.ANCHO_CAMARA / 2, Plataforma.ALTO_CAMARA / 2, 0);
        camara.update();
        vista = new StretchViewport(Plataforma.ANCHO_CAMARA, Plataforma.ALTO_CAMARA, camara);

        batch = new SpriteBatch();

        // Cargar recursos
        assetManager.load("cargando.png", Texture.class);

        assetManager.load("PantallaInicioCargando.png",Texture.class);

        assetManager.finishLoading();
        texturaCargando = assetManager.get("cargando.png");
        spriteCargando = new Sprite(texturaCargando);
        spriteCargando.setPosition(Plataforma.ANCHO_CAMARA / 2 - spriteCargando.getWidth() / 2,
                Plataforma.ALTO_CAMARA / 2 - spriteCargando.getHeight() / 2);

        texturaFondo = assetManager.get("PantallaInicioCargando.png");



        cargarRecursos();
    }

    // Carga los recursos a través del administrador de assets (siguiente pantalla):)
    private void cargarRecursos() {

        if(nivel.equals("Nivel1")) {

            Gdx.app.log("cargarRecursos", "Nivel1");
            // Carga los recursos de la siguiente pantalla (PantallaJuego)
            assetManager.load("Mapa.tmx", TiledMap.class);  // Cargar info del mapa
            assetManager.load("marioSprite.png", Texture.class);
            assetManager.load("marioSpriteIzq.png", Texture.class);
            assetManager.load("salto.png", Texture.class);

            //cargar barra
            assetManager.load("barra.png", Texture.class);
            assetManager.load("barraF.png", Texture.class);

            assetManager.load("HeiDisparar.png", Texture.class);
            assetManager.load("HeiDispararI.png", Texture.class);

            // Cargar imagen
            // Texturas de los botones
            assetManager.load("BtmDerecho.png", Texture.class);
            assetManager.load("BtmIzquierdo.png", Texture.class);
            assetManager.load("BtmSaltar.png", Texture.class);
            assetManager.load("shoot.png", Texture.class);
            assetManager.load("bullet.png", Texture.class);
            assetManager.load("embudo.png", Texture.class);
            assetManager.load("Planta.png", Texture.class);
            assetManager.load("balaPlanta.png", Texture.class);
            assetManager.load("balaEmbudo.png", Texture.class);
            assetManager.load("BtmPausa.png", Texture.class);

            Gdx.app.log("cargarRecursos", "Ya llegue aqui");

            //Para la pausa
            assetManager.load("Pausa.png", Texture.class);
            assetManager.load("BtmPlay.png", Texture.class);
            assetManager.load("back.png", Texture.class);
            assetManager.load("BtmSonido.png", Texture.class);
            assetManager.load("BtmMusic.png", Texture.class);
            assetManager.load("BtmSonidoF.png", Texture.class);
            assetManager.load("BtmMusicF.png", Texture.class);
            assetManager.load("BtmPlay.png", Texture.class);

            // Fin del juego
            assetManager.load("ganaste.png", Texture.class);
            // Efecto al tomar la moneda
            assetManager.load("monedas.mp3", Sound.class);
            assetManager.load("llave.mp3", Sound.class);
            assetManager.load("opendoor.mp3", Sound.class);
            assetManager.load("vidawi.mp3", Sound.class);
            assetManager.load("pistola.mp3", Sound.class);
            assetManager.load("retrocarga.wav", Sound.class);
            assetManager.load("Mute.mp3", Sound.class);

            assetManager.load("find.png",Texture.class);

            //Para cuando pierde
            assetManager.load("GameOver.png",Texture.class);

            //Caminando con pistola
            assetManager.load("caminandoPistola.png",Texture.class);
            assetManager.load("caminandoPistolaI.png",Texture.class);

            //Aseets para cuando salta
            assetManager.load("SaltoDer.png",Texture.class);
            assetManager.load("SaltoIzq.png",Texture.class);


            Gdx.app.log("cargarRecursos", "Terminando...");

        }else if(nivel.equals("MiniGame1")){


            Gdx.app.log("cargarRecursos","Iniciando...");
            // Carga los recursos de la siguiente pantalla (PantallaJuego)
            assetManager.load("MiniGameMapa.tmx", TiledMap.class);  // Cargar info del mapa
            assetManager.load("marioSprite.png", Texture.class);
            assetManager.load("marioSpriteIzq.png", Texture.class);
            assetManager.load("salto.png", Texture.class);


            // Cargar imagen
            // Texturas de los botones
            assetManager.load("BtmDerecho.png", Texture.class);
            assetManager.load("BtmIzquierdo.png", Texture.class);
            assetManager.load("BtmSaltar.png", Texture.class);
            assetManager.load("shoot.png",Texture.class);
            assetManager.load("bullet.png",Texture.class);
            assetManager.load("embudo.png",Texture.class);
            assetManager.load("Planta.png",Texture.class);
            assetManager.load("balaPlanta.png",Texture.class);
            assetManager.load("balaEmbudo.png",Texture.class);
            assetManager.load("BtmPausa.png",Texture.class);
            assetManager.load("embudo.png",Texture.class);

            // Fin del juego
            assetManager.load("ganaste.png", Texture.class);

            // Efecto al tomar la moneda
            assetManager.load("monedas.mp3", Sound.class);
            assetManager.load("llave.mp3", Sound.class);
            assetManager.load("opendoor.mp3", Sound.class);
            assetManager.load("vidawi.mp3", Sound.class);
            assetManager.load("pistola.mp3",Sound.class);
            assetManager.load("Mute.mp3", Sound.class);

            assetManager.load("Apple.png", Texture.class);

            //Para la pausa
            assetManager.load("Pausa.png", Texture.class);
            assetManager.load("BtmPlay.png",Texture.class);
            assetManager.load("back.png",Texture.class);
            assetManager.load("BtmSonido.png",Texture.class);
            assetManager.load("BtmMusic.png",Texture.class);
            assetManager.load("BtmSonidoF.png",Texture.class);
            assetManager.load("BtmMusicF.png",Texture.class);
            assetManager.load("Mute.mp3",Sound.class);
            //Para cuando Gana
            //assetManager.load("Ganar.PNG",Texture.class);

            assetManager.load("CazaCalabazas.png",Texture.class);

            //Aseets para cuando salta
            assetManager.load("SaltoDer.png",Texture.class);
            assetManager.load("SaltoIzq.png",Texture.class);


            Gdx.app.log("cargarRecursos", "Terminando...");


        }else if(nivel.equals("MiniGame2")){
            assetManager.load("inv.tmx", TiledMap.class);  // Cargar info del mapa
            //assetManager.load("Mapa.tmx", TiledMap.class);
            assetManager.load("nave.png", Texture.class);
            assetManager.load("salto.png", Texture.class);


            // Cargar imagen
            // Texturas de los botones
            assetManager.load("BtmDerecho.png", Texture.class);
            assetManager.load("BtmIzquierdo.png", Texture.class);
            assetManager.load("BtmSaltar.png", Texture.class);
            assetManager.load("shoot.png",Texture.class);
            assetManager.load("bala.png",Texture.class);
            assetManager.load("embudo.png",Texture.class);
            //assetManager.load("alien1.png",Texture.class);
            assetManager.load("Planta.png",Texture.class);
            assetManager.load("balaPlanta.png",Texture.class);
            assetManager.load("balaA.png",Texture.class);
            assetManager.load("BtmPausa.png",Texture.class);

            //aliens
            assetManager.load("alien1.png",Texture.class);
            //assetManager.load("alien2.png",Texture.class);
            //assetManager.load("alien3.png",Texture.class);

            //cargar barra
            assetManager.load("barra.png",Texture.class);
            assetManager.load("barraF.png",Texture.class);


            //Para la pausa
            assetManager.load("Pausa.png", Texture.class);
            assetManager.load("BtmPlay.png", Texture.class);
            assetManager.load("back.png", Texture.class);
            assetManager.load("BtmSonido.png", Texture.class);
            assetManager.load("BtmMusic.png", Texture.class);
            assetManager.load("BtmSonidoF.png", Texture.class);
            assetManager.load("BtmMusicF.png", Texture.class);

            //Para cuando pierde
            assetManager.load("GameOver.png",Texture.class);


            // Fin del juego
            assetManager.load("ganaste.png", Texture.class);
            // Efecto al tomar la moneda
            assetManager.load("monedas.mp3", Sound.class);
            assetManager.load("llave.mp3", Sound.class);
            assetManager.load("opendoor.mp3", Sound.class);
            assetManager.load("vidawi.mp3", Sound.class);
            assetManager.load("pistola.mp3", Sound.class);
            assetManager.load("retrocarga.wav", Sound.class);
            assetManager.load("Mute.mp3", Sound.class);


        }
        else if(nivel.equals("Nivel2_A")){

            Gdx.app.log("cargarRecursos", "Iniciando... Nivel2_A");
            // Carga los recursos de la siguiente pantalla (PantallaJuego)
            assetManager.load("Mapa2_A.tmx", TiledMap.class);  // Cargar info del mapa
            assetManager.load("marioSprite.png", Texture.class);
            assetManager.load("marioSpriteIzq.png", Texture.class);
            assetManager.load("salto.png", Texture.class);

            //cargar barra
            assetManager.load("barra.png", Texture.class);
            assetManager.load("barraF.png", Texture.class);


            // Cargar imagen
            // Texturas de los botones
            assetManager.load("BtmDerecho.png", Texture.class);
            assetManager.load("BtmIzquierdo.png", Texture.class);
            assetManager.load("BtmSaltar.png", Texture.class);
            assetManager.load("shoot.png", Texture.class);
            assetManager.load("bullet.png", Texture.class);
            assetManager.load("embudo.png", Texture.class);
            assetManager.load("Regalo.png", Texture.class);
            assetManager.load("proyectil.png", Texture.class);
            assetManager.load("proyectilCopo.png", Texture.class);
            assetManager.load("BtmPausa.png", Texture.class);

            //Para la pausa
            assetManager.load("Pausa.png", Texture.class);
            assetManager.load("BtmPlay.png", Texture.class);
            assetManager.load("back.png", Texture.class);
            assetManager.load("BtmSonido.png", Texture.class);
            assetManager.load("BtmMusic.png", Texture.class);
            assetManager.load("BtmSonidoF.png", Texture.class);
            assetManager.load("BtmMusicF.png", Texture.class);
            // Fin del juego
            assetManager.load("ganaste.png", Texture.class);
            // Efecto al tomar la moneda
            assetManager.load("monedas.mp3", Sound.class);
            assetManager.load("llave.mp3", Sound.class);
            assetManager.load("opendoor.mp3", Sound.class);
            assetManager.load("vidawi.mp3", Sound.class);
            assetManager.load("pistola.mp3", Sound.class);
            assetManager.load("retrocarga.wav", Sound.class);
            assetManager.load("Mute.mp3", Sound.class);

            //Para cuando pierde
            assetManager.load("GameOver.png",Texture.class);



            //Assets para el disparo
            assetManager.load("HeiDisparar.png", Texture.class);
            assetManager.load("HeiDispararI.png", Texture.class);

            //Caminando con pistola
            assetManager.load("caminandoPistola.png",Texture.class);
            assetManager.load("caminandoPistolaI.png",Texture.class);

            //Aseets para cuando salta
            assetManager.load("SaltoDer.png",Texture.class);
            assetManager.load("SaltoIzq.png",Texture.class);


            Gdx.app.log("cargarRecursos", "Terminando...");
        }
        else if(nivel.equals("Nivel2_B")){

            Gdx.app.log("cargarRecursos", "Iniciando... Nivel2_B");
            // Carga los recursos de la siguiente pantalla (PantallaJuego)
            assetManager.load("Mapa2_B.tmx", TiledMap.class);  // Cargar info del mapa
            assetManager.load("marioSprite.png", Texture.class);
            assetManager.load("marioSpriteIzq.png", Texture.class);
            assetManager.load("salto.png", Texture.class);

            //cargar barra
            assetManager.load("barra.png", Texture.class);
            assetManager.load("barraF.png", Texture.class);


            // Cargar imagen
            // Texturas de los botones
            assetManager.load("BtmDerecho.png", Texture.class);
            assetManager.load("BtmIzquierdo.png", Texture.class);
            assetManager.load("BtmSaltar.png", Texture.class);
            assetManager.load("shoot.png", Texture.class);
            assetManager.load("bullet.png", Texture.class);
            assetManager.load("embudo.png", Texture.class);
            assetManager.load("ECalabaza.png", Texture.class);
            assetManager.load("BalaCalabaza.png", Texture.class);
            assetManager.load("Arania.png", Texture.class);
            assetManager.load("BtmPausa.png", Texture.class);

            //Para la pausa
            assetManager.load("Pausa.png", Texture.class);
            assetManager.load("BtmPlay.png", Texture.class);
            assetManager.load("back.png", Texture.class);
            assetManager.load("BtmSonido.png", Texture.class);
            assetManager.load("BtmMusic.png", Texture.class);
            assetManager.load("BtmSonidoF.png", Texture.class);
            assetManager.load("BtmMusicF.png", Texture.class);
            // Fin del juego
            assetManager.load("ganaste.png", Texture.class);
            // Efecto al tomar la moneda
            assetManager.load("monedas.mp3", Sound.class);
            assetManager.load("llave.mp3", Sound.class);
            assetManager.load("opendoor.mp3", Sound.class);
            assetManager.load("vidawi.mp3", Sound.class);
            assetManager.load("pistola.mp3", Sound.class);
            assetManager.load("retrocarga.wav", Sound.class);
            assetManager.load("Mute.mp3", Sound.class);

            //Para cuando pierde
            assetManager.load("GameOver.png",Texture.class);



            //Assets para el disparo
            assetManager.load("HeiDisparar.png", Texture.class);
            assetManager.load("HeiDispararI.png", Texture.class);

            //Caminando con pistola
            assetManager.load("caminandoPistola.png",Texture.class);
            assetManager.load("caminandoPistolaI.png",Texture.class);

            //Aseets para cuando salta
            assetManager.load("SaltoDer.png",Texture.class);
            assetManager.load("SaltoIzq.png",Texture.class);


            Gdx.app.log("cargarRecursos", "Terminando...");
        }
        else if(nivel.equals("Nivel3_A")){

            Gdx.app.log("cargarRecursos", "Iniciando... Nivel3_A");
            // Carga los recursos de la siguiente pantalla (PantallaJuego)
            assetManager.load("Mapa3_A.tmx", TiledMap.class);  // Cargar info del mapa
            assetManager.load("marioSprite.png", Texture.class);
            assetManager.load("marioSpriteIzq.png", Texture.class);
            assetManager.load("salto.png", Texture.class);

            assetManager.load("gana1.jpg", Texture.class);

            //cargar barra
            assetManager.load("barra.png", Texture.class);
            assetManager.load("barraF.png", Texture.class);


            // Cargar imagen
            // Texturas de los botones
            assetManager.load("BtmDerecho.png", Texture.class);
            assetManager.load("BtmIzquierdo.png", Texture.class);
            assetManager.load("BtmSaltar.png", Texture.class);
            assetManager.load("shoot.png", Texture.class);
            assetManager.load("bullet.png", Texture.class);
            assetManager.load("embudo.png", Texture.class);
            assetManager.load("Planta.png", Texture.class);
            assetManager.load("balaPlanta.png", Texture.class);
            assetManager.load("balaEmbudo.png", Texture.class);
            assetManager.load("BtmPausa.png", Texture.class);

            //Para la pausa
            assetManager.load("Pausa.png", Texture.class);
            assetManager.load("BtmPlay.png", Texture.class);
            assetManager.load("back.png", Texture.class);
            assetManager.load("BtmSonido.png", Texture.class);
            assetManager.load("BtmMusic.png", Texture.class);
            assetManager.load("BtmSonidoF.png", Texture.class);
            assetManager.load("BtmMusicF.png", Texture.class);
            // Fin del juego
            assetManager.load("ganaste.png", Texture.class);
            // Efecto al tomar la moneda
            assetManager.load("monedas.mp3", Sound.class);
            assetManager.load("llave.mp3", Sound.class);
            assetManager.load("opendoor.mp3", Sound.class);
            assetManager.load("vidawi.mp3", Sound.class);
            assetManager.load("pistola.mp3", Sound.class);
            assetManager.load("retrocarga.wav", Sound.class);
            assetManager.load("Mute.mp3", Sound.class);

            assetManager.load("GanarJuego.jpg",Texture.class);
            assetManager.load("gana1.jpg",Texture.class);

            //Para cuando pierde
            assetManager.load("GameOver.png",Texture.class);

            //Assets para el disparo
            assetManager.load("HeiDisparar.png", Texture.class);
            assetManager.load("HeiDispararI.png", Texture.class);

            //Caminando con pistola
            assetManager.load("caminandoPistola.png",Texture.class);
            assetManager.load("caminandoPistolaI.png",Texture.class);


            //Aseets para cuando salta
            assetManager.load("SaltoDer.png",Texture.class);
            assetManager.load("SaltoIzq.png",Texture.class);


            Gdx.app.log("cargarRecursos", "Terminando...");
        }
    }

    @Override
    public void render(float delta) {

        // Actualizar carga
        actualizar();

        // Dibujar
        borrarPantalla();
        spriteCargando.setRotation(spriteCargando.getRotation()-10);

        batch.setProjectionMatrix(camara.combined);

        // Entre begin-end dibujamos nuestros objetos en pantalla
        batch.begin();
        batch.draw(texturaFondo,0,0);
        spriteCargando.draw(batch);
        batch.end();
    }

    private void actualizar() {

        if (assetManager.update()) {
            // Terminó la carga, cambiar de pantalla
            if(nivel.equals("Nivel1")){
                plataforma.setScreen(new PantallaJuego(plataforma));
            }else if(nivel.equals("MiniGame1")){
                plataforma.setScreen(new MiniGame1(plataforma));
            }else if(nivel.equals("MiniGame2")){
                plataforma.setScreen(new MiniGame2(plataforma));
            }else if(nivel.equals("Nivel2_A")){
                plataforma.setScreen(new Nivel2_A(plataforma));
            }
            else if(nivel.equals("Nivel2_B")){
                plataforma.setScreen(new Nivel2_B(plataforma));
            }
            else if(nivel.equals("Nivel3_A")){
                plataforma.setScreen(new mx.izo.xportal.Nivel3_A(plataforma));
            }

        } else {
            // Aún no termina la carga de assets, leer el avance
            float avance = assetManager.getProgress()*100;
            //Gdx.app.log("Cargando",avance+"%");
        }
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

    @Override
    public void dispose() {
        texturaCargando.dispose();
        texturaFondo.dispose();
        // Los assets de PantallaJuego se liberan en el método dispose de PantallaJuego
    }

    public void setNivel(String nivel){
        this.nivel = nivel;
    }
}
