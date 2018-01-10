package com.asinfo.as2.financiero.SRI.procesos.servicio;

import com.asinfo.as2.entities.GastoDeducibleSRI;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import javax.ejb.Local;

@Local
public abstract interface ServicioGastoDeducibleSRI
{
  public abstract void guardar(GastoDeducibleSRI paramGastoDeducibleSRI);
  
  public abstract void eliminar(GastoDeducibleSRI paramGastoDeducibleSRI);
  
  public abstract GastoDeducibleSRI buscarPorId(int paramInt);
  
  public abstract List<GastoDeducibleSRI> obtenerListaPorPagina(int paramInt1, int paramInt2, String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract List<GastoDeducibleSRI> obtenerListaCombo(String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract int contarPorCriterio(Map<String, String> paramMap);
  
  public abstract GastoDeducibleSRI cargarDetalle(int paramInt);
  
  public abstract List<GastoDeducibleSRI> obtenerListaPorAnio(int paramInt1, int paramInt2, int paramInt3);
  
  public abstract BigDecimal obtenerPorEmpleadoAnio(int paramInt1, int paramInt2);
}


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.financiero.SRI.procesos.servicio.ServicioGastoDeducibleSRI
 * JD-Core Version:    0.7.0.1
 */