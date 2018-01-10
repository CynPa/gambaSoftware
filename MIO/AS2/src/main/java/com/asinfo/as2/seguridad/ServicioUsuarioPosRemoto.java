package com.asinfo.as2.seguridad;

import com.asinfo.as2.entities.seguridad.EntidadUsuario;
import com.asinfo.as2.excepciones.ExcepcionAS2;
import java.util.List;
import javax.ejb.Remote;

@Remote
public abstract interface ServicioUsuarioPosRemoto
{
  public abstract EntidadUsuario loginPos(String paramString1, String paramString2)
    throws ExcepcionAS2;
  
  public abstract List<EntidadUsuario> obtenerListaAgenteComercial();
  
  public abstract String imprimirMensaje(String paramString);
}


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.seguridad.ServicioUsuarioPosRemoto
 * JD-Core Version:    0.7.0.1
 */