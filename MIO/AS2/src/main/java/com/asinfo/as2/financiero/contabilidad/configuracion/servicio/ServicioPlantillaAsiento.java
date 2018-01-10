package com.asinfo.as2.financiero.contabilidad.configuracion.servicio;

import com.asinfo.as2.entities.PlantillaAsiento;
import com.asinfo.as2.excepciones.AS2Exception;
import com.asinfo.as2.financiero.excepciones.ExcepcionAS2Financiero;
import java.util.List;
import java.util.Map;
import javax.ejb.Local;

@Local
public abstract interface ServicioPlantillaAsiento
{
  public abstract void guardar(PlantillaAsiento paramPlantillaAsiento)
    throws AS2Exception;
  
  public abstract void anular(PlantillaAsiento paramPlantillaAsiento)
    throws ExcepcionAS2Financiero;
  
  public abstract PlantillaAsiento buscarPorId(Integer paramInteger);
  
  public abstract List<PlantillaAsiento> obtenerListaPorPagina(int paramInt1, int paramInt2, String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract List<PlantillaAsiento> obtenerListaCombo(String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract PlantillaAsiento cargarDetalle(Integer paramInteger);
  
  public abstract int contarPorCriterio(Map<String, String> paramMap);
  
  public abstract void calcularTotales(PlantillaAsiento paramPlantillaAsiento);
}


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.financiero.contabilidad.configuracion.servicio.ServicioPlantillaAsiento
 * JD-Core Version:    0.7.0.1
 */