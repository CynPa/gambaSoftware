package com.asinfo.as2.financiero.pagos.configuracion.servicio;

import com.asinfo.as2.entities.MotivoNotaCreditoProveedor;
import java.util.List;
import java.util.Map;
import javax.ejb.Local;

@Local
public abstract interface ServicioMotivoNotaCreditoProveedor
{
  public abstract void guardar(MotivoNotaCreditoProveedor paramMotivoNotaCreditoProveedor);
  
  public abstract void eliminar(MotivoNotaCreditoProveedor paramMotivoNotaCreditoProveedor);
  
  public abstract MotivoNotaCreditoProveedor buscarPorId(int paramInt);
  
  public abstract List<MotivoNotaCreditoProveedor> obtenerListaPorPagina(int paramInt1, int paramInt2, String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract List<MotivoNotaCreditoProveedor> obtenerListaCombo(String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract int contarPorCriterio(Map<String, String> paramMap);
}


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.financiero.pagos.configuracion.servicio.ServicioMotivoNotaCreditoProveedor
 * JD-Core Version:    0.7.0.1
 */