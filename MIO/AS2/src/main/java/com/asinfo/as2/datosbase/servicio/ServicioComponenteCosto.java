package com.asinfo.as2.datosbase.servicio;

import com.asinfo.as2.entities.ComponenteCosto;
import com.asinfo.as2.enumeraciones.TipoComponenteCostoEnum;
import com.asinfo.as2.excepciones.AS2Exception;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.ejb.Local;

@Local
public abstract interface ServicioComponenteCosto
{
  public abstract void guardar(ComponenteCosto paramComponenteCosto)
    throws AS2Exception;
  
  public abstract void eliminar(ComponenteCosto paramComponenteCosto);
  
  public abstract ComponenteCosto buscarPorId(int paramInt);
  
  public abstract List<ComponenteCosto> obtenerListaPorPagina(int paramInt1, int paramInt2, String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract List<ComponenteCosto> obtenerListaCombo(String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract int contarPorCriterio(Map<String, String> paramMap);
  
  public abstract ComponenteCosto cargarDetalle(int paramInt);
  
  public abstract List<ComponenteCosto> buscarComponentePorTipo(int paramInt, TipoComponenteCostoEnum paramTipoComponenteCostoEnum);
  
  public abstract Map<Integer, BigDecimal> obtenerSaldoCuentaDistribuidoPorRuta(ComponenteCosto paramComponenteCosto, Date paramDate1, Date paramDate2)
    throws AS2Exception;
}


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.datosbase.servicio.ServicioComponenteCosto
 * JD-Core Version:    0.7.0.1
 */