package com.asinfo.as2.inventario.configuracion.servicio;

import com.asinfo.as2.entities.Atributo;
import com.asinfo.as2.entities.ValorAtributo;
import java.util.List;
import java.util.Map;
import javax.ejb.Local;

@Local
public abstract interface ServicioValorAtributo
{
  public abstract void guardar(ValorAtributo paramValorAtributo);
  
  public abstract void eliminar(ValorAtributo paramValorAtributo);
  
  public abstract ValorAtributo buscarPorId(int paramInt);
  
  public abstract List<ValorAtributo> obtenerListaPorPagina(int paramInt1, int paramInt2, String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract List<ValorAtributo> obtenerListaCombo(String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract int contarPorCriterio(Map<String, String> paramMap);
  
  public abstract List<ValorAtributo> autocompletarValorAtributo(String paramString, Atributo paramAtributo);
}


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.inventario.configuracion.servicio.ServicioValorAtributo
 * JD-Core Version:    0.7.0.1
 */