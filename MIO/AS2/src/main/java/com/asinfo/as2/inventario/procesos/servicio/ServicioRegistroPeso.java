package com.asinfo.as2.inventario.procesos.servicio;

import com.asinfo.as2.entities.Empresa;
import com.asinfo.as2.entities.FacturaProveedor;
import com.asinfo.as2.entities.Lote;
import com.asinfo.as2.entities.MovimientoInventario;
import com.asinfo.as2.entities.PedidoProveedor;
import com.asinfo.as2.entities.Producto;
import com.asinfo.as2.entities.RegistroPeso;
import com.asinfo.as2.entities.Transportista;
import com.asinfo.as2.enumeraciones.EstadoControlCalidad;
import com.asinfo.as2.enumeraciones.TipoRegistroPeso;
import com.asinfo.as2.excepciones.AS2Exception;
import com.asinfo.as2.excepciones.ExcepcionAS2;
import com.asinfo.as2.financiero.excepciones.ExcepcionAS2Financiero;
import com.asinfo.as2.inventario.excepciones.ExcepcionAS2Inventario;
import com.asinfo.excepciones.ExcepcionAS2Identification;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.ejb.Local;

@Local
public abstract interface ServicioRegistroPeso
{
  public abstract void guardar(RegistroPeso paramRegistroPeso)
    throws AS2Exception, ExcepcionAS2, ExcepcionAS2Financiero, ExcepcionAS2Identification, ExcepcionAS2Inventario;
  
  public abstract void eliminar(RegistroPeso paramRegistroPeso)
    throws AS2Exception;
  
  public abstract RegistroPeso cargarDetalle(int paramInt);
  
  public abstract RegistroPeso buscarPorId(Integer paramInteger);
  
  public abstract List<RegistroPeso> obtenerListaPorPagina(int paramInt1, int paramInt2, String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract int contarPorCriterio(Map<String, String> paramMap);
  
  public abstract void flush();
  
  public abstract List<Object[]> getReporte(int paramInt);
  
  public abstract void guardarParcial(RegistroPeso paramRegistroPeso)
    throws AS2Exception, ExcepcionAS2;
  
  public abstract void totalizarPesoNeto(RegistroPeso paramRegistroPeso);
  
  public abstract List<RegistroPeso> obtenerListaRegistroPesoPendientesPorLiquidar(Empresa paramEmpresa);
  
  public abstract void actualizarLiquidacion(FacturaProveedor paramFacturaProveedor);
  
  public abstract List<Object[]> getReporteFleteTransportistas(int paramInt, Date paramDate1, Date paramDate2, Transportista paramTransportista, boolean paramBoolean1, boolean paramBoolean2);
  
  public abstract List<Object[]> getReporteCalidadMateriaPrima(Date paramDate1, Date paramDate2, EstadoControlCalidad paramEstadoControlCalidad, PedidoProveedor paramPedidoProveedor);
  
  public abstract List<Object[]> getReporteRegistroPesoRecepcionMateriaPrima(Date paramDate1, Date paramDate2, Empresa paramEmpresa, Producto paramProducto, Lote paramLote);
  
  public abstract List<Object[]> getReporteRegistroPesoTransferencia(Date paramDate1, Date paramDate2, Producto paramProducto, MovimientoInventario paramMovimientoInventario, Lote paramLote, TipoRegistroPeso paramTipoRegistroPeso);
  
  public abstract void validar(RegistroPeso paramRegistroPeso)
    throws AS2Exception;
  
  public abstract BigDecimal getCantidadRegistroRecepcionProveedor(RegistroPeso paramRegistroPeso);
  
  public abstract BigDecimal[] calcularRangosPermitidos(RegistroPeso paramRegistroPeso);
  
  public abstract List<Object[]> getReporteProductoCuarentena(Date paramDate, Empresa paramEmpresa, Producto paramProducto, PedidoProveedor paramPedidoProveedor);
  
  public abstract List<RegistroPeso> obtenerListaCombo(String paramString, boolean paramBoolean, Map<String, String> paramMap);
}


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.inventario.procesos.servicio.ServicioRegistroPeso
 * JD-Core Version:    0.7.0.1
 */