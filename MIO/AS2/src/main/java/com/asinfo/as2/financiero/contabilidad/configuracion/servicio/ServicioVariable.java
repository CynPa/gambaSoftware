package com.asinfo.as2.financiero.contabilidad.configuracion.servicio;

import com.asinfo.as2.entities.Variable;
import com.asinfo.as2.financiero.excepciones.ExcepcionAS2Financiero;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.ejb.Local;

@Local
public abstract interface ServicioVariable
{
  public abstract void guardar(Variable paramVariable)
    throws ExcepcionAS2Financiero;
  
  public abstract void eliminar(Variable paramVariable);
  
  public abstract Variable buscarPorId(Integer paramInteger);
  
  public abstract Variable cargarDetalle(int paramInt);
  
  public abstract int contarPorCriterio(Map<String, String> paramMap);
  
  public abstract List<Variable> obtenerListaPorPagina(int paramInt1, int paramInt2, String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract List<Variable> obtenerListaCombo(String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract Variable calcularValorVariable(String paramString, Date paramDate1, Date paramDate2);
}


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.financiero.contabilidad.configuracion.servicio.ServicioVariable
 * JD-Core Version:    0.7.0.1
 */