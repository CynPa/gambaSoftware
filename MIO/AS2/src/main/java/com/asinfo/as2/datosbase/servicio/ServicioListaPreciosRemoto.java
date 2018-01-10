package com.asinfo.as2.datosbase.servicio;

import com.asinfo.as2.entities.DetalleVersionListaPrecios;
import com.asinfo.as2.entities.ListaPrecios;
import com.asinfo.as2.excepciones.ExcepcionAS2;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.ejb.Remote;

@Remote
public abstract interface ServicioListaPreciosRemoto
{
  public abstract void guardar(ListaPrecios paramListaPrecios);
  
  public abstract void eliminar(ListaPrecios paramListaPrecios);
  
  public abstract ListaPrecios buscarPorId(int paramInt);
  
  public abstract List<ListaPrecios> obtenerListaCombo(String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract List<ListaPrecios> obtenerListaCombo();
  
  public abstract DetalleVersionListaPrecios getDatosVersionListaPrecios(int paramInt1, int paramInt2, Date paramDate, Integer paramInteger)
    throws ExcepcionAS2;
  
  public abstract ListaPrecios cargarDetalle(int paramInt);
  
  public abstract List<ListaPrecios> obtenerListaPorPagina(int paramInt1, int paramInt2, String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract int contarPorCriterio(Map<String, String> paramMap);
  
  public abstract List<ListaPrecios> obtenerListaCombo(String paramString, Map<String, String> paramMap);
}


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.datosbase.servicio.ServicioListaPreciosRemoto
 * JD-Core Version:    0.7.0.1
 */