package com.asinfo.as2.financiero.SRI.configuracion.servicio;

import com.asinfo.as2.entities.TipoIdentificacion;
import com.asinfo.as2.entities.sri.CreditoTributarioSRI;
import com.asinfo.as2.entities.sri.TipoComprobanteSRI;
import java.util.List;
import java.util.Map;
import javax.ejb.Local;

@Local
public abstract interface ServicioCreditoTributario
{
  public abstract void guardar(CreditoTributarioSRI paramCreditoTributarioSRI);
  
  public abstract void eliminar(CreditoTributarioSRI paramCreditoTributarioSRI);
  
  public abstract CreditoTributarioSRI buscarPorId(int paramInt);
  
  public abstract List<CreditoTributarioSRI> obtenerListaPorPagina(int paramInt1, int paramInt2, String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract List<CreditoTributarioSRI> obtenerListaCombo(String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract int contarPorCriterio(Map<String, String> paramMap);
  
  public abstract CreditoTributarioSRI cargarDetalle(int paramInt);
  
  public abstract CreditoTributarioSRI buscarPorCodigo(String paramString);
  
  public abstract List<CreditoTributarioSRI> autocompletarCreditoTributario(String paramString, boolean paramBoolean);
  
  public abstract List<CreditoTributarioSRI> buscarPorTipoComprobanteSRI(TipoComprobanteSRI paramTipoComprobanteSRI, TipoIdentificacion paramTipoIdentificacion);
}


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.financiero.SRI.configuracion.servicio.ServicioCreditoTributario
 * JD-Core Version:    0.7.0.1
 */