package com.asinfo.as2.ventas.procesos.servicio;

import com.asinfo.as2.compronteselectronicos.base.DocumentoElectronico;
import com.asinfo.as2.compronteselectronicos.base.EstadoDocumentoElectronico;
import com.asinfo.as2.entities.GuiaRemision;
import com.asinfo.as2.entities.PuntoDeVenta;
import com.asinfo.as2.enumeraciones.Estado;
import com.asinfo.as2.excepciones.AS2Exception;
import com.asinfo.as2.excepciones.ExcepcionAS2;
import com.asinfo.excepciones.ExcepcionAS2Identification;
import java.io.InputStream;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.ejb.Local;

@Local
public abstract interface ServicioGuiaRemision
{
  public abstract void guardar(GuiaRemision paramGuiaRemision)
    throws ExcepcionAS2Identification;
  
  public abstract void eliminar(GuiaRemision paramGuiaRemision);
  
  public abstract void anular(GuiaRemision paramGuiaRemision);
  
  public abstract GuiaRemision buscarPorId(Integer paramInteger);
  
  public abstract GuiaRemision buscarPorDespacho(int paramInt);
  
  public abstract GuiaRemision buscarPorTransferenciaBodega(int paramInt);
  
  public abstract GuiaRemision buscarPorHojaRutaTransportista(int paramInt);
  
  public abstract GuiaRemision cargarSecuencia(GuiaRemision paramGuiaRemision, PuntoDeVenta paramPuntoDeVenta)
    throws ExcepcionAS2;
  
  public abstract GuiaRemision cargarDetalle(int paramInt);
  
  public abstract List<GuiaRemision> obtenerListaPorPagina(int paramInt1, int paramInt2, String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract int contarPorCriterio(Map<String, String> paramMap);
  
  public abstract List<GuiaRemision> migracionGuiaRemision(int paramInt1, String paramString, InputStream paramInputStream, int paramInt2)
    throws ExcepcionAS2, AS2Exception;
  
  public abstract GuiaRemision buscarPorFactura(int paramInt);
  
  public abstract void actualizaGuiaRemisionSRI(int paramInt, Estado paramEstado, EstadoDocumentoElectronico paramEstadoDocumentoElectronico, Date paramDate, String paramString1, String paramString2);
  
  public abstract void enviarMail(GuiaRemision paramGuiaRemision, String paramString)
    throws ExcepcionAS2;
  
  public abstract void enviarMail(GuiaRemision paramGuiaRemision, DocumentoElectronico paramDocumentoElectronico, String paramString)
    throws ExcepcionAS2;
}


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.ventas.procesos.servicio.ServicioGuiaRemision
 * JD-Core Version:    0.7.0.1
 */