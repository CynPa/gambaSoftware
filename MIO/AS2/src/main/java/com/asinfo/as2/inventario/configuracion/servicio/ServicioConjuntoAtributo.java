package com.asinfo.as2.inventario.configuracion.servicio;

import com.asinfo.as2.entities.ConjuntoAtributo;
import java.util.List;
import java.util.Map;
import javax.ejb.Local;

@Local
public abstract interface ServicioConjuntoAtributo
{
  public abstract void guardar(ConjuntoAtributo paramConjuntoAtributo);
  
  public abstract void eliminar(ConjuntoAtributo paramConjuntoAtributo);
  
  public abstract ConjuntoAtributo buscarPorId(int paramInt);
  
  public abstract ConjuntoAtributo cargarDetalle(int paramInt);
  
  public abstract List<ConjuntoAtributo> obtenerListaPorPagina(int paramInt1, int paramInt2, String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract List<ConjuntoAtributo> obtenerListaCombo(String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract int contarPorCriterio(Map<String, String> paramMap);
  
  public abstract List<ConjuntoAtributo> obtenerListaComboPorIndicador(int paramInt, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3);
}


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.inventario.configuracion.servicio.ServicioConjuntoAtributo
 * JD-Core Version:    0.7.0.1
 */