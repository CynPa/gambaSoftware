package com.asinfo.as2.financiero.contabilidad.procesos.servicio;

import com.asinfo.as2.entities.Asiento;
import com.asinfo.as2.entities.DetalleAsiento;
import com.asinfo.as2.entities.PlantillaAsiento;
import com.asinfo.as2.entities.TipoAsiento;
import com.asinfo.as2.enumeraciones.Estado;
import com.asinfo.as2.excepciones.AS2Exception;
import com.asinfo.as2.excepciones.ExcepcionAS2;
import com.asinfo.as2.financiero.excepciones.ExcepcionAS2Financiero;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.ejb.Local;

@Local
public abstract interface ServicioAsiento
{
  public abstract void guardar(Asiento paramAsiento)
    throws ExcepcionAS2Financiero, ExcepcionAS2;
  
  public abstract void anular(Asiento paramAsiento)
    throws ExcepcionAS2Financiero;
  
  public abstract Asiento buscarPorId(Integer paramInteger);
  
  public abstract Asiento cargarDetalle(int paramInt)
    throws ExcepcionAS2Financiero;
  
  public abstract void mayorizarDesmayorizar(Date paramDate1, Date paramDate2, TipoAsiento paramTipoAsiento, Estado paramEstado, int paramInt)
    throws ExcepcionAS2Financiero;
  
  public abstract void mayorizarDesmayorizarPorAsiento(Estado paramEstado, Asiento paramAsiento)
    throws ExcepcionAS2Financiero;
  
  public abstract List<Asiento> obtenerListaPorPagina(int paramInt1, int paramInt2, String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract List<Asiento> obtenerListaCombo(String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract void validarDescuadre(Asiento paramAsiento)
    throws ExcepcionAS2Financiero, ExcepcionAS2;
  
  public abstract Estado getEstado(Integer paramInteger);
  
  public abstract void actualizarEstado(Integer paramInteger, Estado paramEstado)
    throws ExcepcionAS2Financiero;
  
  public abstract void esEditable(Asiento paramAsiento)
    throws ExcepcionAS2Financiero;
  
  public abstract List getReporteAsiento(Asiento paramAsiento, boolean paramBoolean, Boolean paramBoolean1)
    throws ExcepcionAS2Financiero;
  
  public abstract int contarPorCriterio(Map<String, String> paramMap);
  
  public abstract Asiento copiarAsiento(Asiento paramAsiento)
    throws ExcepcionAS2Financiero, ExcepcionAS2;
  
  public abstract void migracionAsiento(String paramString, InputStream paramInputStream, int paramInt1, int paramInt2)
    throws ExcepcionAS2, AS2Exception;
  
  public abstract void eliminarCheque(Asiento paramAsiento)
    throws ExcepcionAS2Financiero;
  
  public abstract Asiento cargarPlantillaAsiento(Asiento paramAsiento, PlantillaAsiento paramPlantillaAsiento, BigDecimal paramBigDecimal);
  
  public abstract void calcularTotales(Asiento paramAsiento);
  
  public abstract void actualizarIndicadorAsientoAerolineas(Integer paramInteger);
  
  public abstract void actualizarMovimientoBancario(DetalleAsiento paramDetalleAsiento)
    throws ExcepcionAS2Financiero, ExcepcionAS2;
  
  public abstract Long verificarExistenciaNumero(Asiento paramAsiento);
}


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.financiero.contabilidad.procesos.servicio.ServicioAsiento
 * JD-Core Version:    0.7.0.1
 */