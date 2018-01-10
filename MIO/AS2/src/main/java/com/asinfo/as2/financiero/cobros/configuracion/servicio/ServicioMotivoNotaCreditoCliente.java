package com.asinfo.as2.financiero.cobros.configuracion.servicio;

import com.asinfo.as2.entities.MotivoNotaCreditoCliente;
import java.util.List;
import java.util.Map;
import javax.ejb.Local;

@Local
public abstract interface ServicioMotivoNotaCreditoCliente
{
  public abstract void guardar(MotivoNotaCreditoCliente paramMotivoNotaCreditoCliente);
  
  public abstract void eliminar(MotivoNotaCreditoCliente paramMotivoNotaCreditoCliente);
  
  public abstract MotivoNotaCreditoCliente buscarPorId(int paramInt);
  
  public abstract List<MotivoNotaCreditoCliente> obtenerListaPorPagina(int paramInt1, int paramInt2, String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract List<MotivoNotaCreditoCliente> obtenerListaCombo(String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract int contarPorCriterio(Map<String, String> paramMap);
}


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.financiero.cobros.configuracion.servicio.ServicioMotivoNotaCreditoCliente
 * JD-Core Version:    0.7.0.1
 */