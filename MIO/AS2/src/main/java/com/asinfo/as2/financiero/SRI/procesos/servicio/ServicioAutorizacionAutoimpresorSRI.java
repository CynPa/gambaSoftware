package com.asinfo.as2.financiero.SRI.procesos.servicio;

import com.asinfo.as2.entities.PuntoDeVenta;
import com.asinfo.as2.entities.sri.AutorizacionAutoimpresorSRI;
import com.asinfo.as2.entities.sri.AutorizacionDocumentoPuntoDeVentaAutoimpresorSRI;
import com.asinfo.as2.enumeraciones.DocumentoBase;
import com.asinfo.as2.enumeraciones.ProcesoAutoimpresorSRIEnum;
import com.asinfo.as2.excepciones.ExcepcionAS2;
import com.asinfo.as2.financiero.excepciones.ExcepcionAS2Financiero;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.ejb.Local;

@Local
public abstract interface ServicioAutorizacionAutoimpresorSRI
{
  public abstract void guardar(AutorizacionAutoimpresorSRI paramAutorizacionAutoimpresorSRI, ProcesoAutoimpresorSRIEnum paramProcesoAutoimpresorSRIEnum, Date paramDate)
    throws ExcepcionAS2Financiero, ExcepcionAS2;
  
  public abstract void eliminar(AutorizacionAutoimpresorSRI paramAutorizacionAutoimpresorSRI);
  
  public abstract AutorizacionAutoimpresorSRI buscarPorId(int paramInt);
  
  public abstract List<AutorizacionAutoimpresorSRI> obtenerListaPorPagina(int paramInt1, int paramInt2, String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract int contarPorCriterio(Map<String, String> paramMap);
  
  public abstract List<AutorizacionAutoimpresorSRI> obtenerListaCombo(String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract AutorizacionAutoimpresorSRI cargarDetalle(int paramInt);
  
  public abstract List<AutorizacionDocumentoPuntoDeVentaAutoimpresorSRI> obtenerAutorizacionDocumentoPuntoDeVentaSRI(Date paramDate, AutorizacionAutoimpresorSRI paramAutorizacionAutoimpresorSRI, ProcesoAutoimpresorSRIEnum paramProcesoAutoimpresorSRIEnum);
  
  public abstract List<Date> obtenerFechaProceso(AutorizacionAutoimpresorSRI paramAutorizacionAutoimpresorSRI, ProcesoAutoimpresorSRIEnum paramProcesoAutoimpresorSRIEnum);
  
  public abstract AutorizacionDocumentoPuntoDeVentaAutoimpresorSRI obtenerAutorizacionDocumentoPuntoDeVentaAutoimpresorSRI(DocumentoBase paramDocumentoBase, PuntoDeVenta paramPuntoDeVenta)
    throws ExcepcionAS2Financiero;
  
  public abstract String obtenerSecuencia(AutorizacionDocumentoPuntoDeVentaAutoimpresorSRI paramAutorizacionDocumentoPuntoDeVentaAutoimpresorSRI)
    throws ExcepcionAS2Financiero;
  
  public abstract void actualizaSecuencia(AutorizacionDocumentoPuntoDeVentaAutoimpresorSRI paramAutorizacionDocumentoPuntoDeVentaAutoimpresorSRI);
  
  public abstract void generarXMLProceso(AutorizacionAutoimpresorSRI paramAutorizacionAutoimpresorSRI, ProcesoAutoimpresorSRIEnum paramProcesoAutoimpresorSRIEnum, Date paramDate)
    throws ExcepcionAS2Financiero;
  
  public abstract AutorizacionAutoimpresorSRI obtenerAutorizacionSRIVigente(int paramInt)
    throws ExcepcionAS2Financiero;
}


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.financiero.SRI.procesos.servicio.ServicioAutorizacionAutoimpresorSRI
 * JD-Core Version:    0.7.0.1
 */