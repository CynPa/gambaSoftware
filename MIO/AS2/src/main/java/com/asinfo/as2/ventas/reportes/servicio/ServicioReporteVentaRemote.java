package com.asinfo.as2.ventas.reportes.servicio;

import com.asinfo.as2.entities.DimensionContable;
import com.asinfo.as2.entities.Empresa;
import com.asinfo.as2.entities.PuntoDeVenta;
import com.asinfo.as2.entities.Recaudador;
import com.asinfo.as2.entities.Subempresa;
import com.asinfo.as2.entities.Sucursal;
import com.asinfo.as2.enumeraciones.DocumentoBase;
import com.asinfo.as2.enumeraciones.TipoVentaEnum;
import com.asinfo.as2.excepciones.ExcepcionAS2;
import com.asinfo.as2.ventas.excepciones.ExcepcionAS2Ventas;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.ejb.Remote;

@Remote
public abstract interface ServicioReporteVentaRemote
{
  public abstract List getListaReporteEstadoCuenta(Date paramDate1, Date paramDate2, Empresa paramEmpresa, Recaudador paramRecaudador, Subempresa paramSubempresa, int paramInt)
    throws ExcepcionAS2Ventas;
  
  public abstract List getListaReporteCorteFecha(Date paramDate, Empresa paramEmpresa, Recaudador paramRecaudador, int paramInt, Subempresa paramSubempresa)
    throws ExcepcionAS2;
  
  public abstract List getAnalisisVencimientoCliente(Empresa paramEmpresa, Recaudador paramRecaudador, int paramInt, Subempresa paramSubempresa)
    throws ExcepcionAS2Ventas;
  
  public abstract List getListaReporteFacturacionResumido(Date paramDate1, Date paramDate2, String paramString1, String paramString2, int paramInt)
    throws ExcepcionAS2Ventas;
  
  public abstract List getListaReporteFacturacionResumido(Date paramDate1, Date paramDate2, String paramString1, String paramString2, int paramInt1, int paramInt2, boolean paramBoolean1, int paramInt3, int paramInt4, Sucursal paramSucursal, PuntoDeVenta paramPuntoDeVenta, TipoVentaEnum paramTipoVentaEnum, boolean paramBoolean2, boolean paramBoolean3, int paramInt5, DocumentoBase paramDocumentoBase, int paramInt6, DimensionContable paramDimensionContable);
  
  public abstract List getReportePedidoSaldoPorDespachar(Date paramDate1, Date paramDate2, int paramInt1, boolean paramBoolean, int paramInt2)
    throws ExcepcionAS2;
  
  public abstract List getListaReporteVentasMargenDescuento(Date paramDate1, Date paramDate2, int paramInt1, BigDecimal paramBigDecimal, int paramInt2)
    throws ExcepcionAS2;
  
  public abstract List getReportePedidoSaldoPorDescacharGenerico(Date paramDate1, Date paramDate2, int paramInt);
  
  public abstract BigDecimal obtenerSaldoEstadoCuenta(int paramInt, Date paramDate, boolean paramBoolean);
  
  public abstract List getReporteNotaDebito(int paramInt);
  
  public abstract List getVencimientos(int paramInt1, Date paramDate, int paramInt2, int paramInt3, int paramInt4)
    throws ExcepcionAS2;
  
  public abstract List getReporteFacturaCliente(int paramInt)
    throws ExcepcionAS2;
  
  public abstract List getReporteVentasProducto(Date paramDate1, Date paramDate2, int paramInt);
  
  public abstract List getReporteVentasCaja(Date paramDate1, Date paramDate2, int paramInt1, int paramInt2, int paramInt3, int paramInt4);
  
  public abstract List getReporteCorteFechaAnticipoClientes(Date paramDate, int paramInt)
    throws ExcepcionAS2;
  
  public abstract List getReporteCorteFechaAnticipoClientesSinLiquidacion(Date paramDate, int paramInt)
    throws ExcepcionAS2;
}


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.ventas.reportes.servicio.ServicioReporteVentaRemote
 * JD-Core Version:    0.7.0.1
 */