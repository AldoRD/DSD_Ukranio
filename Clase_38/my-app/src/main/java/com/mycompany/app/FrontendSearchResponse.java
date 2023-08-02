package com.mycompany.app;  

public class FrontendSearchResponse {
        private String cadena;
        private int cantidad;

        public FrontendSearchResponse(String cadena, int cantidad) {
            this.cadena = cadena;
            this.cantidad = cantidad;
        }

        public String getcadena() {
            return cadena;
        }

        public int getcantidad() {
            return cantidad;
        }
}
