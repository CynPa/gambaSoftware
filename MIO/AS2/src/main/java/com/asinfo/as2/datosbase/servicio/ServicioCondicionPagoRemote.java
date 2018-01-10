package com.asinfo.as2.datosbase.servicio;

import com.asinfo.as2.entities.CondicionPago;
import com.asinfo.as2.excepciones.ExcepcionAS2;
import java.util.List;
import java.util.Map;
import javax.ejb.Remote;

@Remote
public abstract interface ServicioCondicionPagoRemote
{
  public abstract void guardar(CondicionPago paramCondicionPago);
  
  public abstract void eliminar(CondicionPago paramCondicionPago);
  
  public abstract CondicionPago buscarPorId(Integer paramInteger);
  
  public abstract List<CondicionPago> obtenerListaCombo(String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract List<CondicionPago> obtenerListaPorPagina(int paramInt1, int paramInt2, String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract int contarPorCriterio(Map<String, String> paramMap);
  
  public abstract CondicionPago buscarPorCodigo(String paramString);
  
  public abstract CondicionPago buscarCondicionPagoPorDiasPlazo(int paramInt1, int paramInt2)
    throws ExcepcionAS2;
  
  public abstract CondicionPago devuelveCondicionPagoPredeterminada()
    throws ExcepcionAS2;
}


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.datosbase.servicio.ServicioCondicionPagoRemote
 * JD-Core Version:    0.7.0.1
 */