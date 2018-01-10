package com.asinfo.as2.datosbase.servicio;

import com.asinfo.as2.entities.CategoriaEmpresa;
import java.util.List;
import java.util.Map;
import javax.ejb.Remote;

@Remote
public abstract interface ServicioCategoriaEmpresaRemote
{
  public abstract void guardar(CategoriaEmpresa paramCategoriaEmpresa);
  
  public abstract void eliminar(CategoriaEmpresa paramCategoriaEmpresa);
  
  public abstract CategoriaEmpresa buscarPorId(Integer paramInteger);
  
  public abstract List<CategoriaEmpresa> obtenerListaCombo(String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract List<CategoriaEmpresa> obtenerListaPorPagina(int paramInt1, int paramInt2, String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract int contarPorCriterio(Map<String, String> paramMap);
  
  public abstract CategoriaEmpresa cargarDetalle(int paramInt);
  
  public abstract CategoriaEmpresa buscarPorNombre(String paramString);
  
  public abstract CategoriaEmpresa buscarPorCodigo(String paramString);
}


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.datosbase.servicio.ServicioCategoriaEmpresaRemote
 * JD-Core Version:    0.7.0.1
 */