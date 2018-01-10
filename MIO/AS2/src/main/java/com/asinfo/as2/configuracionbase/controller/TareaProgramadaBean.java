/*   1:    */ package com.asinfo.as2.configuracionbase.controller;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.controller.PageControllerAS2;
/*   5:    */ import com.asinfo.as2.entities.Organizacion;
/*   6:    */ import com.asinfo.as2.entities.TareaProgramada;
/*   7:    */ import com.asinfo.as2.enumeraciones.TareaProgramadaEnum;
/*   8:    */ import com.asinfo.as2.programadas.ProgramarTareasBean;
/*   9:    */ import com.asinfo.as2.servicio.ServicioGenerico;
/*  10:    */ import com.asinfo.as2.util.AppUtil;
/*  11:    */ import java.util.ArrayList;
/*  12:    */ import java.util.List;
/*  13:    */ import java.util.Map;
/*  14:    */ import javax.annotation.PostConstruct;
/*  15:    */ import javax.ejb.EJB;
/*  16:    */ import javax.faces.bean.ManagedBean;
/*  17:    */ import javax.faces.bean.ManagedProperty;
/*  18:    */ import javax.faces.bean.ViewScoped;
/*  19:    */ import javax.faces.model.SelectItem;
/*  20:    */ import org.apache.log4j.Logger;
/*  21:    */ import org.primefaces.component.datatable.DataTable;
/*  22:    */ import org.primefaces.model.LazyDataModel;
/*  23:    */ import org.primefaces.model.SortOrder;
/*  24:    */ 
/*  25:    */ @ManagedBean
/*  26:    */ @ViewScoped
/*  27:    */ public class TareaProgramadaBean
/*  28:    */   extends PageControllerAS2
/*  29:    */ {
/*  30:    */   private static final long serialVersionUID = -6642782572366933215L;
/*  31:    */   @EJB
/*  32:    */   private ServicioGenerico<TareaProgramada> servicioTareaProgramada;
/*  33:    */   @ManagedProperty("#{programarTareasBean}")
/*  34:    */   private ProgramarTareasBean programarTareasBean;
/*  35:    */   private TareaProgramada tareaProgramada;
/*  36:    */   private LazyDataModel<TareaProgramada> listaTareaProgramada;
/*  37:    */   private DataTable dtTareaProgramada;
/*  38:    */   
/*  39:    */   @PostConstruct
/*  40:    */   public void init()
/*  41:    */   {
/*  42: 80 */     this.listaTareaProgramada = new LazyDataModel()
/*  43:    */     {
/*  44:    */       private static final long serialVersionUID = -1752987002238164010L;
/*  45:    */       
/*  46:    */       public List<TareaProgramada> load(int startIndex, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters)
/*  47:    */       {
/*  48: 90 */         List<TareaProgramada> lista = new ArrayList();
/*  49: 91 */         boolean ordenar = sortOrder == SortOrder.ASCENDING;
/*  50:    */         
/*  51: 93 */         lista = TareaProgramadaBean.this.servicioTareaProgramada.obtenerListaPorPagina(TareaProgramada.class, startIndex, pageSize, sortField, ordenar, filters);
/*  52: 94 */         TareaProgramadaBean.this.listaTareaProgramada.setRowCount(TareaProgramadaBean.this.servicioTareaProgramada.contarPorCriterio(TareaProgramada.class, filters));
/*  53:    */         
/*  54: 96 */         return lista;
/*  55:    */       }
/*  56:    */     };
/*  57:    */   }
/*  58:    */   
/*  59:    */   public String crearTareaProgramada()
/*  60:    */   {
/*  61:109 */     this.tareaProgramada = new TareaProgramada();
/*  62:110 */     this.tareaProgramada.setIdOrganizacion(AppUtil.getOrganizacion().getIdOrganizacion());
/*  63:111 */     this.tareaProgramada.setSucursal(AppUtil.getSucursal());
/*  64:112 */     this.tareaProgramada.setActivo(true);
/*  65:113 */     return "";
/*  66:    */   }
/*  67:    */   
/*  68:    */   public String limpiar()
/*  69:    */   {
/*  70:122 */     crearTareaProgramada();
/*  71:123 */     return "";
/*  72:    */   }
/*  73:    */   
/*  74:    */   public String editar()
/*  75:    */   {
/*  76:132 */     if ((getTareaProgramada() != null) && (getTareaProgramada().getId() != 0)) {
/*  77:133 */       setEditado(true);
/*  78:    */     } else {
/*  79:135 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/*  80:    */     }
/*  81:137 */     return "";
/*  82:    */   }
/*  83:    */   
/*  84:    */   public String guardar()
/*  85:    */   {
/*  86:    */     try
/*  87:    */     {
/*  88:147 */       this.servicioTareaProgramada.guardar(getTareaProgramada());
/*  89:148 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/*  90:149 */       getProgramarTareasBean().reinicarTareas();
/*  91:150 */       limpiar();
/*  92:151 */       setEditado(false);
/*  93:    */     }
/*  94:    */     catch (Exception e)
/*  95:    */     {
/*  96:153 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/*  97:154 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/*  98:    */     }
/*  99:156 */     return "";
/* 100:    */   }
/* 101:    */   
/* 102:    */   public String eliminar()
/* 103:    */   {
/* 104:165 */     if ((getTareaProgramada() != null) && (getTareaProgramada().getId() != 0)) {
/* 105:    */       try
/* 106:    */       {
/* 107:167 */         this.servicioTareaProgramada.eliminar(getTareaProgramada());
/* 108:168 */         addInfoMessage(getLanguageController().getMensaje("msg_info_eliminar"));
/* 109:169 */         getProgramarTareasBean().reinicarTareas();
/* 110:    */       }
/* 111:    */       catch (Exception e)
/* 112:    */       {
/* 113:171 */         addErrorMessage(getLanguageController().getMensaje("msg_error_eliminar"));
/* 114:172 */         LOG.error("ERROR AL ELIMINAR DATOS", e);
/* 115:    */       }
/* 116:    */     } else {
/* 117:175 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/* 118:    */     }
/* 119:177 */     return "";
/* 120:    */   }
/* 121:    */   
/* 122:    */   public String cargarDatos()
/* 123:    */   {
/* 124:186 */     return "";
/* 125:    */   }
/* 126:    */   
/* 127:    */   public TareaProgramada getTareaProgramada()
/* 128:    */   {
/* 129:194 */     return this.tareaProgramada;
/* 130:    */   }
/* 131:    */   
/* 132:    */   public void setTareaProgramada(TareaProgramada tareaProgramada)
/* 133:    */   {
/* 134:198 */     this.tareaProgramada = tareaProgramada;
/* 135:    */   }
/* 136:    */   
/* 137:    */   public LazyDataModel<TareaProgramada> getListaTareaProgramada()
/* 138:    */   {
/* 139:202 */     return this.listaTareaProgramada;
/* 140:    */   }
/* 141:    */   
/* 142:    */   public void setListaTareaProgramada(LazyDataModel<TareaProgramada> listaTareaProgramada)
/* 143:    */   {
/* 144:206 */     this.listaTareaProgramada = listaTareaProgramada;
/* 145:    */   }
/* 146:    */   
/* 147:    */   public DataTable getDtTareaProgramada()
/* 148:    */   {
/* 149:210 */     return this.dtTareaProgramada;
/* 150:    */   }
/* 151:    */   
/* 152:    */   public void setDtTareaProgramada(DataTable dtTareaProgramada)
/* 153:    */   {
/* 154:214 */     this.dtTareaProgramada = dtTareaProgramada;
/* 155:    */   }
/* 156:    */   
/* 157:    */   public List<SelectItem> getListaTareaProgramadaEnum()
/* 158:    */   {
/* 159:218 */     List<SelectItem> lista = new ArrayList();
/* 160:219 */     for (TareaProgramadaEnum item : TareaProgramadaEnum.values()) {
/* 161:220 */       lista.add(new SelectItem(item, item.getNombre()));
/* 162:    */     }
/* 163:222 */     return lista;
/* 164:    */   }
/* 165:    */   
/* 166:    */   public ProgramarTareasBean getProgramarTareasBean()
/* 167:    */   {
/* 168:226 */     return this.programarTareasBean;
/* 169:    */   }
/* 170:    */   
/* 171:    */   public void setProgramarTareasBean(ProgramarTareasBean programarTareasBean)
/* 172:    */   {
/* 173:230 */     this.programarTareasBean = programarTareasBean;
/* 174:    */   }
/* 175:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.configuracionbase.controller.TareaProgramadaBean
 * JD-Core Version:    0.7.0.1
 */