package com.asinfo.as2.financiero.contabilidad.procesos.servicio;

import com.asinfo.as2.entities.ConfiguracionExtractoBancario;
import com.asinfo.as2.entities.CuentaBancariaOrganizacion;
import com.asinfo.as2.entities.FormaPago;
import com.asinfo.as2.entities.FormaPagoCuentaBancariaOrganizacion;
import com.asinfo.as2.entities.Secuencia;
import com.asinfo.as2.excepciones.AS2Exception;
import com.asinfo.as2.excepciones.ExcepcionAS2;
import java.util.List;
import java.util.Map;
import javax.ejb.Local;

@Local
public abstract interface ServicioCuentaBancariaOrganizacion
{
  public abstract void guardar(CuentaBancariaOrganizacion paramCuentaBancariaOrganizacion)
    throws ExcepcionAS2, AS2Exception;
  
  public abstract void eliminar(CuentaBancariaOrganizacion paramCuentaBancariaOrganizacion);
  
  public abstract CuentaBancariaOrganizacion buscarPorId(Integer paramInteger);
  
  public abstract CuentaBancariaOrganizacion buscarPorCuentaContable(int paramInt);
  
  public abstract List<CuentaBancariaOrganizacion> obtenerListaPorPagina(int paramInt1, int paramInt2, String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract List<CuentaBancariaOrganizacion> obtenerListaCombo(String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract CuentaBancariaOrganizacion cargarDetalle(int paramInt);
  
  public abstract int contarPorCriterio(Map<String, String> paramMap);
  
  public abstract String getReporteCuentaBancaria(int paramInt);
  
  public abstract Secuencia obtenerSecuenciaPorFormaPago(int paramInt1, int paramInt2);
  
  public abstract CuentaBancariaOrganizacion buscarPorNumeroCuentaBancaria(int paramInt, String paramString);
  
  public abstract ConfiguracionExtractoBancario getConfiguracionExtractoBancario(CuentaBancariaOrganizacion paramCuentaBancariaOrganizacion);
  
  public abstract List<FormaPagoCuentaBancariaOrganizacion> getListaCuentaBancariaPorFormaPago(FormaPago paramFormaPago);
  
  public abstract List<CuentaBancariaOrganizacion> cuentaBancariaOrganizacionPorFormaPago(boolean paramBoolean1, boolean paramBoolean2, int paramInt);
  
  public abstract List<CuentaBancariaOrganizacion> getListaCuentaBancariaConCheques(int paramInt);
}


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.financiero.contabilidad.procesos.servicio.ServicioCuentaBancariaOrganizacion
 * JD-Core Version:    0.7.0.1
 */