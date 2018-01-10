package com.asinfo.as2.financiero.contabilidad.procesos.servicio;

import com.asinfo.as2.entities.DetalleAsiento;
import com.asinfo.as2.entities.Ejercicio;
import com.asinfo.as2.financiero.excepciones.ExcepcionAS2Financiero;
import java.util.List;
import java.util.Map;
import javax.ejb.Local;

@Local
public abstract interface ServicioEjercicio
{
  public abstract void guardar(Ejercicio paramEjercicio);
  
  public abstract void eliminar(Ejercicio paramEjercicio);
  
  public abstract Ejercicio buscarPorId(Integer paramInteger);
  
  public abstract List<DetalleAsiento> cerrarEjercicio(int paramInt)
    throws ExcepcionAS2Financiero;
  
  public abstract Ejercicio recuperaEjercicio(int paramInt);
  
  public abstract List<Ejercicio> obtenerListaPorPagina(int paramInt1, int paramInt2, String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract List<Ejercicio> obtenerListaCombo(String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract int contarPorCriterio(Map<String, String> paramMap);
}


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.financiero.contabilidad.procesos.servicio.ServicioEjercicio
 * JD-Core Version:    0.7.0.1
 */