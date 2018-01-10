package com.asinfo.as2.caja.procesos.servicio;

import com.asinfo.as2.entities.AnticipoCliente;
import com.asinfo.as2.entities.CierreCaja;
import com.asinfo.as2.entities.DetalleFormaCobro;
import com.asinfo.as2.excepciones.ExcepcionAS2;
import com.asinfo.as2.financiero.excepciones.ExcepcionAS2Financiero;
import java.util.List;
import java.util.Map;
import javax.ejb.Local;

@Local
public abstract interface ServicioCierreCaja
{
  public abstract void guardar(CierreCaja paramCierreCaja)
    throws ExcepcionAS2;
  
  public abstract void anular(CierreCaja paramCierreCaja)
    throws ExcepcionAS2Financiero;
  
  public abstract CierreCaja buscarPorId(int paramInt);
  
  public abstract List<CierreCaja> obtenerListaPorPagina(int paramInt1, int paramInt2, String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract List<CierreCaja> obtenerListaCombo(String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract int contarPorCriterio(Map<String, String> paramMap);
  
  public abstract CierreCaja cargarDetalle(int paramInt);
  
  public abstract void esEditable(CierreCaja paramCierreCaja)
    throws ExcepcionAS2Financiero;
  
  public abstract List<DetalleFormaCobro> obtenerListaDetalleCierreCaja(int paramInt, String paramString, Integer paramInteger);
  
  public abstract List<AnticipoCliente> obtenerListaDetalleCierreCajaAC(int paramInt, String paramString, Integer paramInteger);
}


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.caja.procesos.servicio.ServicioCierreCaja
 * JD-Core Version:    0.7.0.1
 */