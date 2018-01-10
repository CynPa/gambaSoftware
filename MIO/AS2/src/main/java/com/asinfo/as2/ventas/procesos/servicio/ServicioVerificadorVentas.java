package com.asinfo.as2.ventas.procesos.servicio;

import com.asinfo.as2.entities.Cliente;
import com.asinfo.as2.entities.Cobro;
import com.asinfo.as2.entities.DespachoCliente;
import com.asinfo.as2.entities.FacturaCliente;
import com.asinfo.as2.entities.LiquidacionAnticipoCliente;
import com.asinfo.as2.inventario.excepciones.ExcepcionAS2Inventario;
import com.asinfo.as2.ventas.excepciones.ExcepcionAS2Ventas;
import java.math.BigDecimal;
import java.util.Date;
import javax.ejb.Local;

@Local
public abstract interface ServicioVerificadorVentas
{
  public abstract void actualizarCantidadPorFacturar(FacturaCliente paramFacturaCliente, boolean paramBoolean)
    throws ExcepcionAS2Inventario;
  
  public abstract void actualizarCantidadPorDespachar(DespachoCliente paramDespachoCliente, boolean paramBoolean)
    throws ExcepcionAS2Inventario;
  
  public abstract void actualizarCuentaPorCobrar(Cobro paramCobro, boolean paramBoolean)
    throws ExcepcionAS2Ventas;
  
  public abstract void actualizarCreditoUtilizado(Cliente paramCliente, BigDecimal paramBigDecimal, boolean paramBoolean);
  
  public abstract void actualizarCuentaPorCobrar(LiquidacionAnticipoCliente paramLiquidacionAnticipoCliente, boolean paramBoolean)
    throws ExcepcionAS2Ventas;
  
  public abstract void verificarCupoCredito(Cliente paramCliente, BigDecimal paramBigDecimal)
    throws ExcepcionAS2Ventas;
  
  public abstract void actualizarPrecioFechaUltimaVenta(FacturaCliente paramFacturaCliente);
  
  public abstract BigDecimal getPorcentajeMorosidad(int paramInt, Date paramDate);
  
  public abstract void verificarCupoCredito(Cliente paramCliente, BigDecimal paramBigDecimal, boolean paramBoolean)
    throws ExcepcionAS2Ventas;
  
  public abstract void verificarBloqueoCliente(Cliente paramCliente, Date paramDate)
    throws ExcepcionAS2Ventas;
  
  public abstract void getEmpresaDocumentacionCompleta(Cliente paramCliente, Date paramDate)
    throws ExcepcionAS2Ventas;
  
  public abstract Integer getNumeroFacturasPendientesSinGarantia(Cliente paramCliente);
  
  public abstract String verificarBloqueoClientePedido(Cliente paramCliente, Date paramDate)
    throws ExcepcionAS2Ventas;
}


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.ventas.procesos.servicio.ServicioVerificadorVentas
 * JD-Core Version:    0.7.0.1
 */