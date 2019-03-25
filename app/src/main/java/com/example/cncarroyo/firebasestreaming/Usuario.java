package com.example.cncarroyo.firebasestreaming;

/**
 * Created by cncarroyo on 26-02-2019.
 */

public class Usuario {

    String nombre,contenido,subtitulo,twitter,url;

    public Usuario(String nombre, String contenido, String subtitulo, String twitter, String url) {
        this.nombre = nombre;
        this.contenido = contenido;
        this.subtitulo = subtitulo;
        this.twitter = twitter;
        this.url = url;
    }

    public Usuario() {
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public String getSubtitulo() {
        return subtitulo;
    }

    public void setSubtitulo(String subtitulo) {
        this.subtitulo = subtitulo;
    }

    public String getTwitter() {
        return twitter;
    }

    public void setTwitter(String twitter) {
        this.twitter = twitter;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
