package com.asinfo.as2.ventas.procesos.servicio;

import com.asinfo.as2.entities.ContratoVenta;
import com.asinfo.as2.entities.DetalleValoresContratoVenta;
import com.asinfo.as2.entities.Empresa;
import com.asinfo.as2.entities.FacturaCliente;
import com.asinfo.as2.entities.InterfazContableProceso;
import com.asinfo.as2.entities.Organizacion;
import com.asinfo.as2.entities.Sucursal;
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
public abstract interface ServicioContratoVenta
{
  public abstract void guardar(ContratoVenta paramContratoVenta)
    throws ExcepcionAS2Financiero, ExcepcionAS2, AS2Exception;
  
  public abstract void eliminar(ContratoVenta paramContratoVenta)
    throws ExcepcionAS2;
  
  public abstract ContratoVenta buscarPorId(Integer paramInteger)
    throws ExcepcionAS2;
  
  public abstract List<ContratoVenta> obtenerListaPorPagina(int paramInt1, int paramInt2, String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract ContratoVenta cargarSecuencia(ContratoVenta paramContratoVenta)
    throws ExcepcionAS2;
  
  public abstract ContratoVenta cargarDetalle(ContratoVenta paramContratoVenta);
  
  public abstract int contarPorCriterio(Map<String, String> paramMap);
  
  public abstract ContratoVenta totalizar(ContratoVenta paramContratoVenta);
  
  public abstract ContratoVenta generarDetallesFacturaContratoVenta(ContratoVenta paramContratoVenta)
    throws ExcepcionAS2;
  
  public abstract List getContratoVenta(ContratoVenta paramContratoVenta, Organizacion paramOrganizacion, Sucursal paramSucursal)
    throws ExcepcionAS2;
  
  public abstract List getContratoVentaDevengado(InterfazContableProceso paramInterfazContableProceso, Organizacion paramOrganizacion, Sucursal paramSucursal)
    throws ExcepcionAS2;
  
  public abstract void esEditable(ContratoVenta paramContratoVenta)
    throws ExcepcionAS2;
  
  public abstract List<Object[]> getValoresDevengados(Date paramDate1, Date paramDate2, Empresa paramEmpresa, Organizacion paramOrganizacion, Sucursal paramSucursal, boolean paramBoolean)
    throws ExcepcionAS2;
  
  public abstract List contratoVentaFacturadoVSNoFacturado(ContratoVenta paramContratoVenta, Organizacion paramOrganizacion, Sucursal paramSucursal)
    throws ExcepcionAS2;
  
  public abstract void procesarSaldoInicial(ContratoVenta paramContratoVenta);
  
  public abstract void liberarValoresDevengados(FacturaCliente paramFacturaCliente)
    throws ExcepcionAS2Ventas;
  
  public abstract List<DetalleValoresContratoVenta> obtenerListaDetalleValoresContratoVentaPorFactura(FacturaCliente paramFacturaCliente);
  
  public abstract void generarDevengadosNotaCredito(FacturaCliente paramFacturaCliente);
  
  public abstract void esAnulable(ContratoVenta paramContratoVenta)
    throws ExcepcionAS2;
  
  public abstract ContratoVenta copiarContratoVenta(ContratoVenta paramContratoVenta)
    throws ExcepcionAS2, ExcepcionAS2Financiero;
  
  public abstract HashMap<String, String> getContratosEnFactura(int paramInt1, int paramInt2);
}


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.ventas.procesos.servicio.ServicioContratoVenta
 * JD-Core Version:    0.7.0.1
 */