package com.asinfo.as2.inventario.configuracion.servicio;

import com.asinfo.as2.entities.Atributo;
import com.asinfo.as2.excepciones.ExcepcionAS2;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import javax.ejb.Local;

@Local
public abstract interface ServicioAtributo
{
  public abstract void guardar(Atributo paramAtributo);
  
  public abstract void eliminar(Atributo paramAtributo);
  
  public abstract Atributo buscarPorId(int paramInt);
  
  public abstract List<Atributo> obtenerListaPorPagina(int paramInt1, int paramInt2, String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract List<Atributo> obtenerListaCombo(String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract int contarPorCriterio(Map<String, String> paramMap);
  
  public abstract Atributo cargarDetalle(int paramInt);
  
  public abstract void cargarDetalleAtributo(Atributo paramAtributo, int paramInt1, String paramString, InputStream paramInputStream, int paramInt2)
    throws ExcepcionAS2;
  
  public abstract List getReporteAtributo(int paramInt);
  
  public abstract List<Atributo> obtenerListaComboPorIndicador(int paramInt, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3);
}


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.inventario.configuracion.servicio.ServicioAtributo
 * JD-Core Version:    0.7.0.1
 */