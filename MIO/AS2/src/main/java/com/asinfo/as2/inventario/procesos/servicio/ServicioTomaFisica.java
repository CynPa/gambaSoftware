package com.asinfo.as2.inventario.procesos.servicio;

import com.asinfo.as2.entities.TomaFisica;
import com.asinfo.as2.excepciones.AS2Exception;
import com.asinfo.as2.excepciones.ExcepcionAS2;
import java.util.List;
import java.util.Map;
import javax.ejb.Local;

@Local
public abstract interface ServicioTomaFisica
{
  public abstract void guardar(TomaFisica paramTomaFisica)
    throws ExcepcionAS2;
  
  public abstract void eliminar(TomaFisica paramTomaFisica);
  
  public abstract TomaFisica buscarPorId(int paramInt);
  
  public abstract TomaFisica cargarDetalle(int paramInt);
  
  public abstract List<TomaFisica> obtenerListaPorPagina(int paramInt1, int paramInt2, String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract List<TomaFisica> obtenerListaCombo(String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract int contarPorCriterio(Map<String, String> paramMap);
  
  public abstract void finalizar(TomaFisica paramTomaFisica)
    throws ExcepcionAS2, AS2Exception;
}


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.inventario.procesos.servicio.ServicioTomaFisica
 * JD-Core Version:    0.7.0.1
 */