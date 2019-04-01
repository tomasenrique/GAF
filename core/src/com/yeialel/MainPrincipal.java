package com.yeialel;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class MainPrincipal extends ApplicationAdapter {

	private SpriteBatch spriteBatch_letra; //
	private BitmapFont bitmapFont_letra; // para escribir texto

	private int alto, ancho; // sera para obtener el tamaño de la pantalla

	//Datos para usar la imagen atlas
	private TextureRegion[] textureRegion_Frames; // cantidad de imagenes por sprite
	private Animation animation;  // animacion del sprite
	private float duracion = 0f; // duracion de la animacion

	private TextureAtlas atlas; // imagen compuesta por varias imagenes(sprite)
	private TextureRegion textureRegionDude;
	public SpriteBatch batch; //necesario para poder mostrar la animacion

	
	@Override
	public void create () {
		// lo siguente servira para poner texto
		spriteBatch_letra = new SpriteBatch();
		bitmapFont_letra = new BitmapFont();
		bitmapFont_letra.setColor(Color.valueOf("#7BC34A")); // color verde


		batch = new SpriteBatch();
		atlas = new TextureAtlas("atlas_creado.atlas");// de donde cogemos las imagenes
		TextureRegion region = atlas.findRegion("paloma"); // la imagen que se coge primero
		textureRegionDude = new TextureRegion(region,0,0,2304,256); // define el tamaño del sprite de la imagen paloma
		TextureRegion[][] temp = textureRegionDude.split(textureRegionDude.getRegionWidth()/9, textureRegionDude.getRegionHeight());
		textureRegion_Frames = new TextureRegion[temp.length * temp[0].length];

		int index = 0;
		for (int i = 0; i < temp.length; i++){// esto devuelve las imagenes cortadas de una en una
			for (int j = 0; j < temp[0].length; j++){
				textureRegion_Frames[index++] = temp[i][j];
			}
		}
		//crea la animacion con un tiempo de duracion para cada imagen cortada o frame
		animation = new Animation(0.1f, textureRegion_Frames);




	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 1, 1, 1); //Definirá el color del fondo.
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);//indica que el buffer está habilitado para escribir colores

		//toma el tamaño de la pantalla como referencia
		alto = Gdx.graphics.getHeight();
		ancho = Gdx.graphics.getWidth();

		// letra del titulo
		spriteBatch_letra.begin();// inicio de la imagen
		bitmapFont_letra.draw(spriteBatch_letra,"Mis Imagenes", 900, 1000); // posicion del titulo
		spriteBatch_letra.end(); // fin

		duracion += Gdx.graphics.getDeltaTime();// tiempo de duracion de la animacion
		TextureRegion anim = (TextureRegion) animation.getKeyFrame(duracion, true); // repite o no constantemente
		batch.begin();
		batch.draw(anim,300,300);
		batch.end();


	}
	
	@Override
	public void dispose () {
		// para el rexto de titulo
		spriteBatch_letra.dispose();
		bitmapFont_letra.dispose();
		//imagen de paloma del sprite atlas
		atlas.dispose();
		batch.dispose();




	}
}
