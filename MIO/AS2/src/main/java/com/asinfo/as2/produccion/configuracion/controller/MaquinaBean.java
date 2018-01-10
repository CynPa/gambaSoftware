/*   1:    */ package com.asinfo.as2.produccion.configuracion.controller;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.controller.PageControllerAS2;
/*   5:    */ import com.asinfo.as2.datosbase.servicio.ServicioCentroTrabajo;
/*   6:    */ import com.asinfo.as2.entities.CentroTrabajo;
/*   7:    */ import com.asinfo.as2.entities.Organizacion;
/*   8:    */ import com.asinfo.as2.entities.Sucursal;
/*   9:    */ import com.asinfo.as2.entities.produccion.Maquina;
/*  10:    */ import com.asinfo.as2.produccion.configuracion.servicio.ServicioMaquina;
/*  11:    */ import com.asinfo.as2.util.AppUtil;
/*  12:    */ import java.util.ArrayList;
/*  13:    */ import java.util.List;
/*  14:    */ import java.util.Map;
/*  15:    */ import javax.annotation.PostConstruct;
/*  16:    */ import javax.ejb.EJB;
/*  17:    */ import javax.faces.bean.ManagedBean;
/*  18:    */ import javax.faces.bean.ViewScoped;
/*  19:    */ import org.apache.log4j.Logger;
/*  20:    */ import org.primefaces.component.datatable.DataTable;
/*  21:    */ import org.primefaces.model.LazyDataModel;
/*  22:    */ import org.primefaces.model.SortOrder;
/*  23:    */ 
/*  24:    */ @ManagedBean
/*  25:    */ @ViewScoped
/*  26:    */ public class MaquinaBean
/*  27:    */   extends PageControllerAS2
/*  28:    */ {
/*  29:    */   private static final long serialVersionUID = 1424377436906270962L;
/*  30:    */   @EJB
/*  31:    */   private ServicioMaquina servicioMaquina;
/*  32:    */   @EJB
/*  33:    */   private ServicioCentroTrabajo servicioCentroTrabajo;
/*  34:    */   private Maquina maquina;
/*  35:    */   private LazyDataModel<Maquina> listaMaquina;
/*  36:    */   private List<CentroTrabajo> listaCentroTrabajo;
/*  37:    */   private DataTable dtMaquina;
/*  38:    */   
/*  39:    */   @PostConstruct
/*  40:    */   public void init()
/*  41:    */   {
/*  42: 81 */     this.listaMaquina = new LazyDataModel()
/*  43:    */     {
/*  44:    */       private static final long serialVersionUID = 1L;
/*  45:    */       
/*  46:    */       public List<Maquina> load(int startIndex, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters)
/*  47:    */       {
/*  48: 88 */         List<Maquina> lista = new ArrayList();
/*  49: 89 */         boolean ordenar = sortOrder == SortOrder.ASCENDING;
/*  50:    */         
/*  51: 91 */         filters.put("idOrganizacion", "" + AppUtil.getOrganizacion().getIdOrganizacion());
/*  52: 92 */         lista = MaquinaBean.this.servicioMaquina.obtenerListaPorPagina(startIndex, pageSize, sortField, ordenar, filters);
/*  53:    */         
/*  54: 94 */         MaquinaBean.this.listaMaquina.setRowCount(MaquinaBean.this.servicioMaquina.contarPorCriterio(filters));
/*  55:    */         
/*  56: 96 */         return lista;
/*  57:    */       }
/*  58:    */     };
/*  59:    */   }
/*  60:    */   
/*  61:    */   private void crearEntidad()
/*  62:    */   {
/*  63:114 */     this.maquina = new Maquina();
/*  64:115 */     this.maquina.setIdOrganizacion(AppUtil.getOrganizacion().getIdOrganizacion());
/*  65:116 */     this.maquina.setIdSucursal(AppUtil.getSucursal().getIdSucursal());
/*  66:117 */     this.maquina.setActivo(true);
/*  67:    */   }
/*  68:    */   
/*  69:    */   public String editar()
/*  70:    */   {
/*  71:126 */     if (getMaquina().getIdMaquina() > 0) {
/*  72:128 */       setEditado(true);
/*  73:    */     } else {
/*  74:130 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/*  75:    */     }
/*  76:132 */     return "";
/*  77:    */   }
/*  78:    */   
/*  79:    */   public String guardar()
/*  80:    */   {
/*  81:    */     try
/*  82:    */     {
/*  83:142 */       this.servicioMaquina.guardar(this.maquina);
/*  84:143 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/*  85:144 */       limpiar();
/*  86:145 */       setEditado(false);
/*  87:    */     }
/*  88:    */     catch (Exception e)
/*  89:    */     {
/*  90:147 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/*  91:148 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/*  92:    */     }
/*  93:150 */     return "";
/*  94:    */   }
/*  95:    */   
/*  96:    */   public String eliminar()
/*  97:    */   {
/*  98:    */     try
/*  99:    */     {
/* 100:160 */       this.servicioMaquina.eliminar(this.maquina);
/* 101:161 */       addInfoMessage(getLanguageController().getMensaje("msg_info_eliminar"));
/* 102:    */     }
/* 103:    */     catch (Exception e)
/* 104:    */     {
/* 105:163 */       addErrorMessage(getLanguageController().getMensaje("msg_error_eliminar"));
/* 106:164 */       LOG.error("ERROR AL ELIMINAR DATOS", e);
/* 107:    */     }
/* 108:166 */     return "";
/* 109:    */   }
/* 110:    */   
/* 111:    */   public String cargarDatos()
/* 112:    */   {
/* 113:175 */     return "";
/* 114:    */   }
/* 115:    */   
/* 116:    */   public String limpiar()
/* 117:    */   {
/* 118:184 */     crearEntidad();
/* 119:185 */     return "";
/* 120:    */   }
/* 121:    */   
/* 122:    */   public Maquina getMaquina()
/* 123:    */   {
/* 124:198 */     return this.maquina;
/* 125:    */   }
/* 126:    */   
/* 127:    */   public void setMaquina(Maquina maquina)
/* 128:    */   {
/* 129:208 */     this.maquina = maquina;
/* 130:    */   }
/* 131:    */   
/* 132:    */   public LazyDataModel<Maquina> getListaMaquina()
/* 133:    */   {
/* 134:217 */     return this.listaMaquina;
/* 135:    */   }
/* 136:    */   
/* 137:    */   public void setListaMaquina(LazyDataModel<Maquina> listaMaquina)
/* 138:    */   {
/* 139:227 */     this.listaMaquina = listaMaquina;
/* 140:    */   }
/* 141:    */   
/* 142:    */   public DataTable getDtMaquina()
/* 143:    */   {
/* 144:236 */     return this.dtMaquina;
/* 145:    */   }
/* 146:    */   
/* 147:    */   public void setDtMaquina(DataTable dtMaquina)
/* 148:    */   {
/* 149:246 */     this.dtMaquina = dtMaquina;
/* 150:    */   }
/* 151:    */   
/* 152:    */   public List<CentroTrabajo> getListaCentroTrabajo()
/* 153:    */   {
/* 154:255 */     if (this.listaCentroTrabajo == null) {
/* 155:256 */       this.listaCentroTrabajo = this.servicioCentroTrabajo.obtenerListaCombo("nombre", true, null);
/* 156:    */     }
/* 157:258 */     return this.listaCentroTrabajo;
/* 158:    */   }
/* 159:    */   
/* 160:    */   public void setListaCentroTrabajo(List<CentroTrabajo> listaCentroTrabajo)
/* 161:    */   {
/* 162:268 */     this.listaCentroTrabajo = listaCentroTrabajo;
/* 163:    */   }
/* 164:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.produccion.configuracion.controller.MaquinaBean
 * JD-Core Version:    0.7.0.1
 */