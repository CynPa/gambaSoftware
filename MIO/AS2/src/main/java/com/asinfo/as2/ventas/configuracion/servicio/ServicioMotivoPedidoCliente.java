package com.asinfo.as2.ventas.configuracion.servicio;

import com.asinfo.as2.entities.MotivoPedidoCliente;
import java.util.List;
import java.util.Map;
import javax.ejb.Local;

@Local
public abstract interface ServicioMotivoPedidoCliente
{
  public abstract void guardar(MotivoPedidoCliente paramMotivoPedidoCliente);
  
  public abstract void eliminar(MotivoPedidoCliente paramMotivoPedidoCliente);
  
  public abstract MotivoPedidoCliente buscarPorId(int paramInt);
  
  public abstract List<MotivoPedidoCliente> obtenerListaPorPagina(int paramInt1, int paramInt2, String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract List<MotivoPedidoCliente> obtenerListaCombo(String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract int contarPorCriterio(Map<String, String> paramMap);
}


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.ventas.configuracion.servicio.ServicioMotivoPedidoCliente
 * JD-Core Version:    0.7.0.1
 */