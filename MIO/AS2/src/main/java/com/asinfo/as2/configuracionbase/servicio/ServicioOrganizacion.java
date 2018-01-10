package com.asinfo.as2.configuracionbase.servicio;

import com.asinfo.as2.clases.CatalogosACopiar;
import com.asinfo.as2.entities.Organizacion;
import com.asinfo.as2.entities.ProcesoOrganizacion;
import com.asinfo.as2.entities.seguridad.EntidadSistema;
import com.asinfo.as2.excepciones.AS2Exception;
import com.asinfo.as2.excepciones.ExcepcionAS2;
import com.asinfo.excepciones.ExcepcionAS2Identification;
import java.util.List;
import java.util.Map;
import javax.ejb.Local;

@Local
public abstract interface ServicioOrganizacion
{
  public abstract void guardar(Organizacion paramOrganizacion)
    throws ExcepcionAS2, ExcepcionAS2Identification;
  
  public abstract void eliminar(Organizacion paramOrganizacion);
  
  public abstract Organizacion buscarPorId(Integer paramInteger);
  
  public abstract Organizacion cargarDetalle(int paramInt);
  
  public abstract List<Organizacion> obtenerListaPorPagina(int paramInt1, int paramInt2, String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract List<Organizacion> obtenerListaCombo(String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract int contarPorCriterio(Map<String, String> paramMap);
  
  public abstract List<Organizacion> obtenerOrganizacionPorUsuario(int paramInt);
  
  public abstract void guardar(Organizacion paramOrganizacion1, List<CatalogosACopiar> paramList, Organizacion paramOrganizacion2)
    throws ExcepcionAS2, ExcepcionAS2Identification;
  
  public abstract Long contarNumeroOrganizaciones();
  
  public abstract Integer actualizarSecuenciaInicioSerie(Organizacion paramOrganizacion);
  
  public abstract String obtenerDireccionMatriz(int paramInt);
  
  public abstract void guardarListaProcesoOrganizacion(List<ProcesoOrganizacion> paramList)
    throws AS2Exception;
  
  public abstract List<Organizacion> obtenerOrganizacionPorUsuario(int paramInt1, int paramInt2, int paramInt3, String paramString, boolean paramBoolean);
  
  public abstract List<Organizacion> obtenerOrganizacionPorUsuario(int paramInt1, int paramInt2, int paramInt3, String paramString, boolean paramBoolean, EntidadSistema paramEntidadSistema);
  
  public abstract Organizacion cargarOrganizacionConfiguracion(int paramInt);
  
  public abstract List<Organizacion> obtenerOrganizacionPorUsuario(int paramInt, EntidadSistema paramEntidadSistema);
}


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.configuracionbase.servicio.ServicioOrganizacion
 * JD-Core Version:    0.7.0.1
 */