package com.asinfo.as2.inventario.configuracion.servicio;

import com.asinfo.as2.entities.Chofer;
import com.asinfo.as2.excepciones.AS2Exception;
import com.asinfo.excepciones.ExcepcionAS2Identification;
import java.util.List;
import java.util.Map;
import javax.ejb.Local;

@Local
public abstract interface ServicioChofer
{
  public abstract void guardar(Chofer paramChofer)
    throws ExcepcionAS2Identification, AS2Exception;
  
  public abstract void eliminar(Chofer paramChofer);
  
  public abstract Chofer buscarPorId(Integer paramInteger);
  
  public abstract List<Chofer> obtenerListaPorPagina(int paramInt1, int paramInt2, String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract List<Chofer> obtenerListaCombo(String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract int contarPorCriterio(Map<String, String> paramMap);
  
  public abstract Chofer cargarDetalle(int paramInt);
}


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.inventario.configuracion.servicio.ServicioChofer
 * JD-Core Version:    0.7.0.1
 */