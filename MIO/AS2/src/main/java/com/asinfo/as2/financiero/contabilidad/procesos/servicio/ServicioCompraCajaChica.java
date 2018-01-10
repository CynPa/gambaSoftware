package com.asinfo.as2.financiero.contabilidad.procesos.servicio;

import com.asinfo.as2.compras.excepciones.ExcepcionAS2Compras;
import com.asinfo.as2.entities.CompraCajaChica;
import com.asinfo.as2.excepciones.AS2Exception;
import com.asinfo.as2.excepciones.ExcepcionAS2;
import com.asinfo.as2.financiero.excepciones.ExcepcionAS2Financiero;
import com.asinfo.as2.ventas.excepciones.ExcepcionAS2Ventas;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.Local;

@Local
public abstract interface ServicioCompraCajaChica
{
  public abstract void guardar(CompraCajaChica paramCompraCajaChica)
    throws ExcepcionAS2Financiero, ExcepcionAS2, AS2Exception;
  
  public abstract void eliminar(CompraCajaChica paramCompraCajaChica)
    throws ExcepcionAS2Financiero, ExcepcionAS2, ExcepcionAS2Compras;
  
  public abstract CompraCajaChica buscarPorId(Integer paramInteger);
  
  public abstract CompraCajaChica cargarDetalle(int paramInt);
  
  public abstract List<CompraCajaChica> obtenerListaPorPagina(int paramInt1, int paramInt2, String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract List<CompraCajaChica> obtenerListaCombo(String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract void esEditable(CompraCajaChica paramCompraCajaChica)
    throws ExcepcionAS2Compras, ExcepcionAS2Financiero;
  
  public abstract CompraCajaChica cargarInformacionSRI(Integer paramInteger)
    throws ExcepcionAS2Compras;
  
  public abstract List getListaReporteCompraCajaChica(Date paramDate1, Date paramDate2, String paramString1, String paramString2, int paramInt1, int paramInt2, int paramInt3, boolean paramBoolean)
    throws ExcepcionAS2Ventas;
  
  public abstract int contarPorCriterio(Map<String, String> paramMap);
  
  public abstract void actualizarAtributoEntidad(CompraCajaChica paramCompraCajaChica, HashMap<String, Object> paramHashMap);
}


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.financiero.contabilidad.procesos.servicio.ServicioCompraCajaChica
 * JD-Core Version:    0.7.0.1
 */