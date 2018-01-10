package com.asinfo.as2.compras.procesos.servicio;

import com.asinfo.as2.compras.excepciones.ExcepcionAS2Compras;
import com.asinfo.as2.entities.FacturaProveedor;
import com.asinfo.as2.entities.PuntoDeVenta;
import com.asinfo.as2.excepciones.ExcepcionAS2;
import com.asinfo.as2.financiero.excepciones.ExcepcionAS2Financiero;
import java.util.List;
import java.util.Map;
import javax.ejb.Local;

@Local
public abstract interface ServicioNotaDebitoProveedor
{
  public abstract FacturaProveedor guardar(FacturaProveedor paramFacturaProveedor)
    throws ExcepcionAS2, ExcepcionAS2Compras, ExcepcionAS2Financiero;
  
  public abstract void eliminar(FacturaProveedor paramFacturaProveedor)
    throws ExcepcionAS2;
  
  public abstract FacturaProveedor buscarPorId(int paramInt)
    throws ExcepcionAS2;
  
  public abstract List<FacturaProveedor> obtenerListaPorPagina(int paramInt1, int paramInt2, String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract List<FacturaProveedor> obtenerListaCombo(String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract int contarPorCriterio(Map<String, String> paramMap);
  
  public abstract FacturaProveedor cargarDetalle(int paramInt);
  
  public abstract long verificaExistenciaNumero(FacturaProveedor paramFacturaProveedor);
  
  public abstract void generarCuentaPorPagar(FacturaProveedor paramFacturaProveedor)
    throws ExcepcionAS2;
  
  public abstract void contabilizar(FacturaProveedor paramFacturaProveedor)
    throws ExcepcionAS2, ExcepcionAS2Financiero;
  
  public abstract FacturaProveedor totalizar(FacturaProveedor paramFacturaProveedor)
    throws ExcepcionAS2Compras;
  
  public abstract FacturaProveedor totalizarImpuesto(FacturaProveedor paramFacturaProveedor)
    throws ExcepcionAS2Compras;
  
  public abstract void cargarSecuencia(FacturaProveedor paramFacturaProveedor, PuntoDeVenta paramPuntoDeVenta)
    throws ExcepcionAS2;
}


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.compras.procesos.servicio.ServicioNotaDebitoProveedor
 * JD-Core Version:    0.7.0.1
 */