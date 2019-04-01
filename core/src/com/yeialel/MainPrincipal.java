package com.yeialel;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class MainPrincipal extends ApplicationAdapter {

	private int alto, ancho; // sera para obtener el tamaño de la pantalla
	private int x, y; // para el cambio de posicion de la animacion

	//Datos para usar la imagen atlas
	private TextureRegion[] textureRegion_Frames; // cantidad de imagenes por sprite
	private Animation animation;  // animacion del sprite para la paloma
	private float duracion = 0f; // duracion de la animacion

	private TextureAtlas atlas; // imagen compuesta por varias imagenes(sprite)
	private TextureRegion textureRegionPaloma, textureRegionDino, textureRegionCaballero;

	private SpriteBatch batch; //necesario para poder mostrar la animacion y las imagenes
	private Sprite sprite;// para girar el dino

	
	@Override
	public void create () {
		//toma el tamaño de la pantalla como referencia
		alto = Gdx.graphics.getHeight(); // 1080
		ancho = Gdx.graphics.getWidth(); // 1794
		x = 1500;

		// servira para almacenar las imagenes, animaciones
		batch = new SpriteBatch(); // para poner mostrar la imagenes y las animaciones

		atlas = new TextureAtlas("atlas_creado.atlas");// de donde cogemos todas las imagenes

		//------------------------------------------------------------------------------------------
		//PALOMA => ANIMACION
		TextureRegion paloma = atlas.findRegion("paloma"); // // se coge las imagenes de la paloma del atlas
		textureRegionPaloma = new TextureRegion(paloma,0,0,2304,256); // define el tamaño del sprite de la imagen paloma
		//temp servira para almacenar la candidad de imagenes que tiene la animacion
		TextureRegion[][] temp = textureRegionPaloma.split(textureRegionPaloma.getRegionWidth()/9, textureRegionPaloma.getRegionHeight());
		textureRegion_Frames = new TextureRegion[temp.length * temp[0].length];//

		int index = 0;// es el indice para recorrer el array de imagenes
		for (int i = 0; i < temp.length; i++){// esto devuelve las imagenes cortadas de una en una
			for (int j = 0; j < temp[0].length; j++){
				textureRegion_Frames[index++] = temp[i][j];
			}
		}
		//crea la animacion con un tiempo de duracion para cada imagen cortada o frame
		animation = new Animation(0.1f, textureRegion_Frames);

		//------------------------------------------------------------------------------------------
		//DINOSAURIO => IMAGEN GIRADA 45 GRADOS
		TextureRegion dino = atlas.findRegion("dino"); // se coge la imagen de dino del atlas
		sprite = new Sprite(dino,0,0,256,256); // centrado y tamaño de la imagen dino
		sprite.rotate(45);  // no rota la imagen en 45 grados

		//------------------------------------------------------------------------------------------
		//CABALLERO => IMAGEN
		TextureRegion caballero = atlas.findRegion("caballero");// se coge la imagen de caballero del atlas
		textureRegionCaballero = new TextureRegion(caballero, 0,0,256,256); // centrado y tamaño de la imagen caballero
	}

	@Override
	public void render () {
		// todo lo que va aqui sera usado en un bucle
		Gdx.gl.glClearColor(1, 1, 1, 1); //Definirá el color del fondo.
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);//indica que el buffer está habilitado para escribir colores

		//datos para la animacion de la paloma
		duracion += Gdx.graphics.getDeltaTime();// tiempo de duracion de la animacion
		TextureRegion anim = (TextureRegion) animation.getKeyFrame(duracion, true); // repite o no constantemente con el boleano

		//------------------------------------------------------------------------------------------
		// dentro del batch tiene que ir todas las imagenes
		batch.begin();

		if(x > 0){ // ida
			batch.draw(anim,x -= 3,300);//posicion de la animacion de paloma
		}else if(x < ancho){  // retorno
			batch.draw(anim,x += ancho,300);//posicion de la animacion de paloma
		}
		sprite.draw(batch);//imagen de dinosaurio
		sprite.setPosition(ancho - 700,alto - 300);


		batch.draw(textureRegionCaballero, 800,800);// posicion de la imagen del caballero

		batch.end();
		//------------------------------------------------------------------------------------------
	}
	
	@Override
	public void dispose () {
		// aqui se elimina los datos de la memoria grafica

		atlas.dispose();//limpia de la memoria de la imagen atlas
		batch.dispose();// limpia la memoria las imagenes de la paloma, dino y caballero
	}
}
