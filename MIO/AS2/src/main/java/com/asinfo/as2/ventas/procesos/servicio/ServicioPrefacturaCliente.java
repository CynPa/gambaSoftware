package com.asinfo.as2.ventas.procesos.servicio;

import com.asinfo.as2.entities.AjustePrefacturaCliente;
import com.asinfo.as2.entities.Asiento;
import com.asinfo.as2.entities.FacturaCliente;
import com.asinfo.as2.entities.PrefacturaCliente;
import com.asinfo.as2.excepciones.AS2Exception;
import com.asinfo.as2.excepciones.ExcepcionAS2;
import com.asinfo.as2.financiero.excepciones.ExcepcionAS2Financiero;
import com.asinfo.as2.ventas.excepciones.ExcepcionAS2Ventas;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.ejb.Local;

@Local
public abstract interface ServicioPrefacturaCliente
{
  public abstract void guardar(PrefacturaCliente paramPrefacturaCliente)
    throws ExcepcionAS2;
  
  public abstract void eliminar(PrefacturaCliente paramPrefacturaCliente)
    throws ExcepcionAS2;
  
  public abstract PrefacturaCliente buscarPorId(Integer paramInteger)
    throws ExcepcionAS2;
  
  public abstract void totalizar(AjustePrefacturaCliente paramAjustePrefacturaCliente)
    throws ExcepcionAS2Ventas;
  
  public abstract void totalizarImpuesto(AjustePrefacturaCliente paramAjustePrefacturaCliente)
    throws ExcepcionAS2Ventas;
  
  public abstract List<PrefacturaCliente> obtenerListaPorPagina(int paramInt1, int paramInt2, String paramString, boolean paramBoolean, Map<String, String> paramMap)
    throws ExcepcionAS2;
  
  public abstract int contarPorCriterio(Map<String, String> paramMap);
  
  public abstract void anular(PrefacturaCliente paramPrefacturaCliente)
    throws ExcepcionAS2Ventas, ExcepcionAS2Financiero;
  
  public abstract List<PrefacturaCliente> obtenerListaCombo(String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract List<PrefacturaCliente> autocompletarPrefactura(String paramString);
  
  public abstract PrefacturaCliente cargarDetalle(int paramInt);
  
  public abstract List<AjustePrefacturaCliente> getListaAjustePrefacturaCliente(int paramInt);
  
  public abstract void actualizarAjusteActivo(int paramInt1, int paramInt2);
  
  public abstract AjustePrefacturaCliente obtenerAjustePrefacturaActivo(int paramInt);
  
  public abstract void contabilizar(AjustePrefacturaCliente paramAjustePrefacturaCliente)
    throws ExcepcionAS2, AS2Exception;
  
  public abstract void liberarPrefacturaCliente(FacturaCliente paramFacturaCliente)
    throws ExcepcionAS2;
  
  public abstract List<PrefacturaCliente> getListaPrefacturaCliente(FacturaCliente paramFacturaCliente);
  
  public abstract List<Object[]> getReportePrefacturaCliente(int paramInt)
    throws ExcepcionAS2;
  
  public abstract List<Asiento> listaCodigosAsiento(int paramInt);
  
  public abstract void contabilizarAjustesPrefacturaCliente(Date paramDate1, Date paramDate2)
    throws ExcepcionAS2, AS2Exception;
}


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.ventas.procesos.servicio.ServicioPrefacturaCliente
 * JD-Core Version:    0.7.0.1
 */