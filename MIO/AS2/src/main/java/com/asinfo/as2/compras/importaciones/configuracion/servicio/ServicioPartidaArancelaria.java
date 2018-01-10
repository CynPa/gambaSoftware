package com.asinfo.as2.compras.importaciones.configuracion.servicio;

import com.asinfo.as2.compras.excepciones.ExcepcionAS2Compras;
import com.asinfo.as2.entities.PartidaArancelaria;
import java.util.List;
import java.util.Map;
import javax.ejb.Local;

@Local
public abstract interface ServicioPartidaArancelaria
{
  public abstract void guardar(PartidaArancelaria paramPartidaArancelaria)
    throws ExcepcionAS2Compras;
  
  public abstract void eliminar(PartidaArancelaria paramPartidaArancelaria);
  
  public abstract PartidaArancelaria buscarPorId(int paramInt);
  
  public abstract List<PartidaArancelaria> obtenerListaPorPagina(int paramInt1, int paramInt2, String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract List<PartidaArancelaria> obtenerListaCombo(String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract int contarPorCriterio(Map<String, String> paramMap);
  
  public abstract PartidaArancelaria cargarDetalle(int paramInt);
}


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.compras.importaciones.configuracion.servicio.ServicioPartidaArancelaria
 * JD-Core Version:    0.7.0.1
 */