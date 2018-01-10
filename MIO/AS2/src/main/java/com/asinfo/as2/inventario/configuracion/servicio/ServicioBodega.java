package com.asinfo.as2.inventario.configuracion.servicio;

import com.asinfo.as2.entities.Bodega;
import com.asinfo.as2.entities.UsuarioBodega;
import com.asinfo.as2.excepciones.ExcepcionAS2;
import java.util.List;
import java.util.Map;
import javax.ejb.Local;

@Local
public abstract interface ServicioBodega
{
  public abstract void guardar(Bodega paramBodega);
  
  public abstract void eliminar(Bodega paramBodega);
  
  public abstract Bodega buscarPorId(Integer paramInteger);
  
  public abstract Bodega cargarDetalle(Bodega paramBodega);
  
  public abstract List<Bodega> obtenerListaPorPagina(int paramInt1, int paramInt2, String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract List<Bodega> obtenerListaCombo(String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract List<Bodega> obtenerBodegaCombo(String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract int contarPorCriterio(Map<String, String> paramMap);
  
  public abstract Bodega buscarPorCodigo(String paramString)
    throws ExcepcionAS2;
  
  public abstract List<Bodega> obtenerListaComboPorUsuario(int paramInt1, int paramInt2);
  
  public abstract List<UsuarioBodega> obtenerListaComboPorUsuarioBodega(int paramInt1, int paramInt2);
  
  public abstract List<Bodega> obtenerListaComboPorUsuario(int paramInt1, int paramInt2, int paramInt3);
}


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.inventario.configuracion.servicio.ServicioBodega
 * JD-Core Version:    0.7.0.1
 */