package com.asinfo.as2.financiero.contabilidad.procesos.servicio;

import com.asinfo.as2.clases.DetalleInterfazContableProceso;
import com.asinfo.as2.entities.Asiento;
import com.asinfo.as2.entities.CriterioDistribucion;
import com.asinfo.as2.entities.DetalleAsiento;
import com.asinfo.as2.entities.DetalleCierreCaja;
import com.asinfo.as2.entities.DocumentoContabilizacion;
import com.asinfo.as2.entities.GuiaAerea;
import com.asinfo.as2.entities.InterfazContableProceso;
import com.asinfo.as2.entities.aerolineas.Cass;
import com.asinfo.as2.excepciones.AS2Exception;
import com.asinfo.as2.excepciones.ExcepcionAS2;
import com.asinfo.as2.financiero.excepciones.ExcepcionAS2Financiero;
import com.asinfo.as2.ventas.excepciones.ExcepcionAS2Ventas;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import javax.ejb.Local;
import org.primefaces.event.FileUploadEvent;

@Local
public abstract interface ServicioInterfazContableProceso
{
  public abstract void guardar(InterfazContableProceso paramInterfazContableProceso)
    throws ExcepcionAS2Financiero, ExcepcionAS2;
  
  public abstract void eliminar(InterfazContableProceso paramInterfazContableProceso);
  
  public abstract InterfazContableProceso buscarPorId(int paramInt);
  
  public abstract List<InterfazContableProceso> obtenerListaPorPagina(int paramInt1, int paramInt2, String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract List<InterfazContableProceso> obtenerListaCombo(String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract int contarPorCriterio(Map<String, String> paramMap);
  
  public abstract InterfazContableProceso cargarDetalle(int paramInt);
  
  public abstract Asiento generarAsiento(InterfazContableProceso paramInterfazContableProceso)
    throws ExcepcionAS2, ExcepcionAS2Financiero, AS2Exception;
  
  public abstract void esEditable(InterfazContableProceso paramInterfazContableProceso)
    throws ExcepcionAS2Financiero, ExcepcionAS2Ventas;
  
  public abstract List<DetalleCierreCaja> obtenerListaDetalleCierreCaja(InterfazContableProceso paramInterfazContableProceso, int paramInt);
  
  public abstract void anular(InterfazContableProceso paramInterfazContableProceso)
    throws ExcepcionAS2Financiero, ExcepcionAS2;
  
  public abstract void actualizarEstadoFacturasInterfazContable(InterfazContableProceso paramInterfazContableProceso)
    throws ExcepcionAS2, ExcepcionAS2Financiero;
  
  public abstract void contabilizar(InterfazContableProceso paramInterfazContableProceso, Asiento paramAsiento)
    throws ExcepcionAS2Financiero, ExcepcionAS2;
  
  public abstract void calcularValorTotal(InterfazContableProceso paramInterfazContableProceso);
  
  public abstract Asiento contabilizarDepositoCaja(InterfazContableProceso paramInterfazContableProceso)
    throws ExcepcionAS2, ExcepcionAS2Financiero, AS2Exception;
  
  public abstract List<DetalleAsiento> generarAsiento(Asiento paramAsiento, List<DetalleInterfazContableProceso> paramList, DocumentoContabilizacion paramDocumentoContabilizacion, List<CriterioDistribucion> paramList1, boolean paramBoolean1, boolean paramBoolean2)
    throws ExcepcionAS2Financiero, AS2Exception;
  
  public abstract Asiento ajustarDiferencias(Asiento paramAsiento);
  
  public abstract List<DetalleAsiento> generarAsiento(Asiento paramAsiento, List<DetalleInterfazContableProceso> paramList, DocumentoContabilizacion paramDocumentoContabilizacion, List<CriterioDistribucion> paramList1, boolean paramBoolean)
    throws ExcepcionAS2Financiero, AS2Exception;
  
  public abstract String migrarCass(FileUploadEvent paramFileUploadEvent, Cass paramCass, Asiento paramAsiento, BigDecimal paramBigDecimal1, BigDecimal paramBigDecimal2, List<GuiaAerea> paramList1, List<GuiaAerea> paramList2, List<GuiaAerea> paramList3, int paramInt)
    throws IOException, AS2Exception, ExcepcionAS2;
  
  public abstract void validarArchivoCass(int paramInt, String paramString1, String paramString2, Integer paramInteger1, Integer paramInteger2, Integer paramInteger3, Integer paramInteger4, Integer paramInteger5, String paramString3)
    throws AS2Exception;
  
  public abstract void redondearAsiento(List<DetalleAsiento> paramList);
}


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.financiero.contabilidad.procesos.servicio.ServicioInterfazContableProceso
 * JD-Core Version:    0.7.0.1
 */