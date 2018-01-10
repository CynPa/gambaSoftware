package com.asinfo.as2.financiero.cobros.procesos.servicio;

import com.asinfo.as2.entities.Caja;
import com.asinfo.as2.entities.Cobro;
import com.asinfo.as2.entities.CuentaPorCobrar;
import com.asinfo.as2.entities.DetalleFormaCobro;
import com.asinfo.as2.entities.Documento;
import com.asinfo.as2.entities.Empresa;
import com.asinfo.as2.entities.FacturaCliente;
import com.asinfo.as2.entities.PuntoDeVenta;
import com.asinfo.as2.entities.Sucursal;
import com.asinfo.as2.entities.seguridad.EntidadUsuario;
import com.asinfo.as2.enumeraciones.Estado;
import com.asinfo.as2.excepciones.AS2Exception;
import com.asinfo.as2.excepciones.ExcepcionAS2;
import com.asinfo.as2.financiero.excepciones.ExcepcionAS2Financiero;
import com.asinfo.as2.ventas.excepciones.ExcepcionAS2Ventas;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.Local;
import org.jdom2.Element;
import org.jdom2.JDOMException;

@Local
public abstract interface ServicioCobro
{
  public abstract void guardar(Cobro paramCobro)
    throws ExcepcionAS2, ExcepcionAS2Financiero, AS2Exception;
  
  public abstract void eliminar(Cobro paramCobro);
  
  public abstract Cobro buscarPorId(Integer paramInteger);
  
  public abstract Cobro cargarDetalle(int paramInt);
  
  public abstract void validar(Cobro paramCobro)
    throws ExcepcionAS2, ExcepcionAS2Financiero;
  
  public abstract boolean validaAsientoCobro(Cobro paramCobro)
    throws ExcepcionAS2, ExcepcionAS2Financiero;
  
  public abstract void anularCobro(Cobro paramCobro)
    throws ExcepcionAS2, ExcepcionAS2Financiero, ExcepcionAS2Ventas;
  
  public abstract List<Cobro> obtenerListaPorPagina(int paramInt1, int paramInt2, String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract void contabilizar(Cobro paramCobro)
    throws ExcepcionAS2, ExcepcionAS2Financiero, AS2Exception;
  
  public abstract void actualizarEstado(int paramInt, Estado paramEstado);
  
  public abstract void esEditable(Cobro paramCobro)
    throws ExcepcionAS2Financiero;
  
  public abstract void cargarFacturasPendientes(Cobro paramCobro, String paramString);
  
  public abstract void cargarFacturasPendientes(Cobro paramCobro, int paramInt);
  
  public abstract void cargarFacturasPendientes(Cobro paramCobro, int paramInt, String paramString);
  
  public abstract int contarPorCriterio(Map<String, String> paramMap);
  
  public abstract void actualizaSaldoBloqueadoCXC(Cobro paramCobro, boolean paramBoolean);
  
  public abstract void procesarProtesto(Cobro paramCobro, List<DetalleFormaCobro> paramList, Date paramDate)
    throws ExcepcionAS2Financiero, ExcepcionAS2, AS2Exception;
  
  public abstract void esEditable(Cobro paramCobro, boolean paramBoolean)
    throws ExcepcionAS2Financiero;
  
  public abstract List<Cobro> obtenerListaCombo(String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract void actualizarGarantiaCliente(DetalleFormaCobro paramDetalleFormaCobro);
  
  public abstract void contabilizarProtesto(Cobro paramCobro, Date paramDate)
    throws ExcepcionAS2Financiero, ExcepcionAS2, AS2Exception;
  
  public abstract List<DetalleFormaCobro> obtenerListaPorPaginaConsulta(int paramInt1, int paramInt2, String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract int contarPorCriterioConsulta(Map<String, String> paramMap);
  
  public abstract void guardarFormaCobro(Cobro paramCobro)
    throws ExcepcionAS2Financiero, ExcepcionAS2, AS2Exception;
  
  public abstract void anularProtesto(Cobro paramCobro)
    throws ExcepcionAS2Financiero, ExcepcionAS2;
  
  public abstract Cobro buscarPorNumero(int paramInt, String paramString);
  
  public abstract List<CuentaPorCobrar> buscarCuentaPorCobrarPorNumeroFacturaCliente(int paramInt1, int paramInt2, String paramString);
  
  public abstract void liberarCobro(DetalleFormaCobro paramDetalleFormaCobro)
    throws AS2Exception;
  
  public abstract void cargarFacturasPendientes(Cobro paramCobro);
  
  public abstract void prorratearDetalleCobroFormaCobro(Cobro paramCobro)
    throws AS2Exception;
  
  public abstract void guardarCabecera(Cobro paramCobro);
  
  public abstract List<Object[]> reporteChequePosfechadoDetallado(int paramInt, Sucursal paramSucursal, Empresa paramEmpresa, Date paramDate1, Date paramDate2, EntidadUsuario paramEntidadUsuario);
  
  public abstract void cargarSecuencia(Cobro paramCobro)
    throws ExcepcionAS2;
  
  public abstract List<Cobro> cargarXML(List<Object[]> paramList, List<Element> paramList1, Element paramElement, int paramInt, Documento paramDocumento, Sucursal paramSucursal, Caja paramCaja, String paramString, boolean paramBoolean)
    throws IOException, JDOMException, ExcepcionAS2, AS2Exception;
  
  public abstract CuentaPorCobrar buscarCuentaPorCobrarPorId(int paramInt);
  
  public abstract void actualizarAtributoEntidad(Cobro paramCobro, HashMap<String, Object> paramHashMap);
  
  public abstract void actualizarVoucher(Cobro paramCobro, DetalleFormaCobro paramDetalleFormaCobro, PuntoDeVenta paramPuntoDeVenta, FacturaCliente paramFacturaCliente);
}


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.financiero.cobros.procesos.servicio.ServicioCobro
 * JD-Core Version:    0.7.0.1
 */