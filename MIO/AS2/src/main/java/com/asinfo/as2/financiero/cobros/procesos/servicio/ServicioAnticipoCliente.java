package com.asinfo.as2.financiero.cobros.procesos.servicio;

import com.asinfo.as2.clases.ReporteAnticipoCliente;
import com.asinfo.as2.entities.AnticipoCliente;
import com.asinfo.as2.entities.CategoriaEmpresa;
import com.asinfo.as2.entities.Cobro;
import com.asinfo.as2.entities.Empresa;
import com.asinfo.as2.entities.FacturaCliente;
import com.asinfo.as2.entities.MovimientoInventario;
import com.asinfo.as2.enumeraciones.Estado;
import com.asinfo.as2.excepciones.AS2Exception;
import com.asinfo.as2.excepciones.ExcepcionAS2;
import com.asinfo.as2.financiero.excepciones.ExcepcionAS2Financiero;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.Local;

@Local
public abstract interface ServicioAnticipoCliente
{
  public abstract void guardar(AnticipoCliente paramAnticipoCliente)
    throws ExcepcionAS2, ExcepcionAS2Financiero, AS2Exception;
  
  public abstract void eliminar(AnticipoCliente paramAnticipoCliente);
  
  public abstract AnticipoCliente buscarPorId(int paramInt);
  
  public abstract List<AnticipoCliente> obtenerAnticiposPendientes(int paramInt);
  
  public abstract void anularAnticipoCliente(AnticipoCliente paramAnticipoCliente)
    throws ExcepcionAS2, ExcepcionAS2Financiero;
  
  public abstract List<AnticipoCliente> obtenerListaPorPagina(int paramInt1, int paramInt2, String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract void contabilizar(AnticipoCliente paramAnticipoCliente)
    throws ExcepcionAS2, ExcepcionAS2Financiero, AS2Exception;
  
  public abstract AnticipoCliente generaAnticipoDevolucionCliente(Empresa paramEmpresa, MovimientoInventario paramMovimientoInventario, FacturaCliente paramFacturaCliente, AnticipoCliente paramAnticipoCliente);
  
  public abstract AnticipoCliente cargarDetalle(int paramInt);
  
  public abstract BigDecimal obtenerSaldoAnticipo(int paramInt, Date paramDate);
  
  public abstract void actualizarEstado(Integer paramInteger, Estado paramEstado);
  
  public abstract void cargarSecuencia(AnticipoCliente paramAnticipoCliente)
    throws ExcepcionAS2;
  
  public abstract int contarPorCriterio(Map<String, String> paramMap);
  
  public abstract void guardarDevolucion(AnticipoCliente paramAnticipoCliente)
    throws ExcepcionAS2, ExcepcionAS2Financiero;
  
  public abstract void validaFechaDevolucionAnticipo(Date paramDate1, Date paramDate2)
    throws ExcepcionAS2Financiero;
  
  public abstract void anularAnticipoCliente(AnticipoCliente paramAnticipoCliente, boolean paramBoolean1, boolean paramBoolean2)
    throws ExcepcionAS2;
  
  public abstract void esEditable(AnticipoCliente paramAnticipoCliente, boolean paramBoolean1, boolean paramBoolean2)
    throws ExcepcionAS2Financiero;
  
  public abstract BigDecimal getSaldoAnticipo(AnticipoCliente paramAnticipoCliente);
  
  public abstract AnticipoCliente buscarPorCobro(Cobro paramCobro);
  
  public abstract List<AnticipoCliente> listaAnticiposClientesMasivos(BigDecimal paramBigDecimal, int paramInt, Empresa paramEmpresa);
  
  public abstract void actualizarAtributoEntidad(AnticipoCliente paramAnticipoCliente, HashMap<String, Object> paramHashMap);
  
  public abstract void contabilizarDevolucion(AnticipoCliente paramAnticipoCliente)
    throws ExcepcionAS2, ExcepcionAS2Financiero, AS2Exception;
  
  public abstract void anularDevolverAnticipoCliente(AnticipoCliente paramAnticipoCliente)
    throws ExcepcionAS2, ExcepcionAS2Financiero;
  
  public abstract List<ReporteAnticipoCliente> obtenerAnticiposClientes(CategoriaEmpresa paramCategoriaEmpresa, Integer paramInteger1, Date paramDate, Integer paramInteger2);
}


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.financiero.cobros.procesos.servicio.ServicioAnticipoCliente
 * JD-Core Version:    0.7.0.1
 */