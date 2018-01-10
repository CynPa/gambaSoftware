package com.asinfo.as2.datosbase.servicio;

import com.asinfo.as2.entities.DetalleVersionListaPrecios;
import com.asinfo.as2.entities.ListaPrecios;
import com.asinfo.as2.entities.VersionListaPrecios;
import com.asinfo.as2.excepciones.AS2Exception;
import com.asinfo.as2.excepciones.ExcepcionAS2;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.ejb.Local;

@Local
public abstract interface ServicioListaPrecios
{
  public abstract void guardar(ListaPrecios paramListaPrecios)
    throws AS2Exception;
  
  public abstract void eliminar(ListaPrecios paramListaPrecios);
  
  public abstract ListaPrecios buscarPorId(int paramInt);
  
  public abstract List<ListaPrecios> obtenerListaCombo(String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract List<ListaPrecios> obtenerListaCombo();
  
  public abstract DetalleVersionListaPrecios getDatosVersionListaPrecios(int paramInt1, int paramInt2, Date paramDate, Integer paramInteger, String paramString)
    throws ExcepcionAS2;
  
  public abstract ListaPrecios cargarDetalle(int paramInt);
  
  public abstract List<ListaPrecios> obtenerListaPorPagina(int paramInt1, int paramInt2, String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract int contarPorCriterio(Map<String, String> paramMap);
  
  public abstract ListaPrecios obtenerListaPreciosVigente(int paramInt);
  
  public abstract List<VersionListaPrecios> getZonaListaPreciosProductoNuevo(int paramInt1, int paramInt2, int paramInt3);
  
  public abstract void guardarListaDetalleVersionListaPrecios(List<DetalleVersionListaPrecios> paramList);
  
  public abstract List getReporteListaPrecios(int paramInt);
  
  public abstract VersionListaPrecios getUltimaVersionListaPrecios(int paramInt);
  
  public abstract List<ListaPrecios> autocompletarListaPrecios(String paramString);
  
  public abstract List<VersionListaPrecios> getListaVersionListaPrecios(ListaPrecios paramListaPrecios);
  
  public abstract BigDecimal precioProducto(VersionListaPrecios paramVersionListaPrecios, String paramString);
  
  public abstract DetalleVersionListaPrecios getDatosVersionListaPrecios(int paramInt1, int paramInt2, Date paramDate, Integer paramInteger, String paramString, boolean paramBoolean)
    throws ExcepcionAS2;
  
  public abstract List<ListaPrecios> autocompletarListaPrecios(String paramString, boolean paramBoolean, int paramInt);
}


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.datosbase.servicio.ServicioListaPrecios
 * JD-Core Version:    0.7.0.1
 */