package com.asinfo.as2.financiero.pagos.procesos.servicio;

import com.asinfo.as2.entities.LiquidacionAnticipoProveedor;
import com.asinfo.as2.enumeraciones.Estado;
import com.asinfo.as2.excepciones.AS2Exception;
import com.asinfo.as2.excepciones.ExcepcionAS2;
import com.asinfo.as2.financiero.excepciones.ExcepcionAS2Financiero;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.ejb.Local;

@Local
public abstract interface ServicioLiquidacionAnticipoProveedor
{
  public abstract void guardar(LiquidacionAnticipoProveedor paramLiquidacionAnticipoProveedor)
    throws ExcepcionAS2, ExcepcionAS2Financiero;
  
  public abstract void eliminar(LiquidacionAnticipoProveedor paramLiquidacionAnticipoProveedor);
  
  public abstract LiquidacionAnticipoProveedor buscarPorId(Integer paramInteger);
  
  public abstract LiquidacionAnticipoProveedor cargarDetalle(int paramInt);
  
  public abstract void contabilizar(LiquidacionAnticipoProveedor paramLiquidacionAnticipoProveedor)
    throws ExcepcionAS2, ExcepcionAS2Financiero, AS2Exception;
  
  public abstract void esEditable(LiquidacionAnticipoProveedor paramLiquidacionAnticipoProveedor)
    throws ExcepcionAS2Financiero;
  
  public abstract void anular(LiquidacionAnticipoProveedor paramLiquidacionAnticipoProveedor)
    throws ExcepcionAS2, ExcepcionAS2Financiero;
  
  public abstract void actualizarEstado(int paramInt, Estado paramEstado);
  
  public abstract int contarPorCriterio(Map<String, String> paramMap);
  
  public abstract List<LiquidacionAnticipoProveedor> obtenerListaPorPagina(int paramInt1, int paramInt2, String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract void validaFechaLiquidacionAnticipo(Date paramDate1, Date paramDate2)
    throws ExcepcionAS2Financiero;
  
  public abstract LiquidacionAnticipoProveedor cargarFacturasPendientes(LiquidacionAnticipoProveedor paramLiquidacionAnticipoProveedor, int paramInt);
  
  public abstract List<LiquidacionAnticipoProveedor> getLiquidacionAnticipoProveedorPorAnticipoProveedor(int paramInt);
}


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.financiero.pagos.procesos.servicio.ServicioLiquidacionAnticipoProveedor
 * JD-Core Version:    0.7.0.1
 */