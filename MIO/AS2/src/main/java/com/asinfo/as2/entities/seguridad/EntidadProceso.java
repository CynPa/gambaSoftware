/*   1:    */ package com.asinfo.as2.entities.seguridad;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.entities.EntidadBase;
/*   4:    */ import com.asinfo.as2.entities.Modulo;
/*   5:    */ import javax.persistence.Column;
/*   6:    */ import javax.persistence.Entity;
/*   7:    */ import javax.persistence.FetchType;
/*   8:    */ import javax.persistence.GeneratedValue;
/*   9:    */ import javax.persistence.GenerationType;
/*  10:    */ import javax.persistence.Id;
/*  11:    */ import javax.persistence.JoinColumn;
/*  12:    */ import javax.persistence.ManyToOne;
/*  13:    */ import javax.persistence.Table;
/*  14:    */ import javax.persistence.TableGenerator;
/*  15:    */ import javax.validation.constraints.NotNull;
/*  16:    */ import javax.validation.constraints.Size;
/*  17:    */ 
/*  18:    */ @Entity
/*  19:    */ @Table(name="proceso", uniqueConstraints={@javax.persistence.UniqueConstraint(columnNames={"id_organizacion", "id_sistema", "view_id"})})
/*  20:    */ public class EntidadProceso
/*  21:    */   extends EntidadBase
/*  22:    */ {
/*  23:    */   private static final long serialVersionUID = 1L;
/*  24:    */   @Id
/*  25:    */   @TableGenerator(name="proceso", initialValue=0, allocationSize=50)
/*  26:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="proceso")
/*  27:    */   @Column(name="id_proceso", unique=true, nullable=false)
/*  28:    */   private int idProceso;
/*  29:    */   @Column(name="id_organizacion", nullable=false)
/*  30:    */   private int idOrganizacion;
/*  31:    */   @Column(name="id_sucursal", nullable=false)
/*  32:    */   private int idSucursal;
/*  33:    */   @Column(name="view_id", length=200, nullable=false)
/*  34:    */   @NotNull
/*  35:    */   @Size(max=200)
/*  36:    */   private String viewId;
/*  37:    */   @Column(name="view_name", length=100, nullable=false)
/*  38:    */   @NotNull
/*  39:    */   @Size(max=100)
/*  40:    */   private String viewName;
/*  41:    */   @Column(name="orden", nullable=false)
/*  42:    */   private int orden;
/*  43:    */   @Column(name="indicador_mostrar_menu", nullable=false)
/*  44:    */   private boolean indicadorMostrarMenu;
/*  45:    */   @Column(name="descripcion", length=200)
/*  46:    */   @Size(max=200)
/*  47:    */   private String descripcion;
/*  48:    */   @NotNull
/*  49:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  50:    */   @JoinColumn(name="id_modulo", nullable=false)
/*  51:    */   private Modulo modulo;
/*  52:    */   @NotNull
/*  53:    */   @ManyToOne(fetch=FetchType.EAGER)
/*  54:    */   @JoinColumn(name="id_sistema", nullable=false)
/*  55:    */   private EntidadSistema sistema;
/*  56:    */   @Column(name="activo", nullable=false)
/*  57:    */   private boolean activo;
/*  58:    */   
/*  59:    */   public EntidadProceso() {}
/*  60:    */   
/*  61:    */   public EntidadProceso(int idProceso, String viewName, String descripcion)
/*  62:    */   {
/*  63:101 */     this.idProceso = idProceso;
/*  64:102 */     this.viewName = viewName;
/*  65:103 */     this.descripcion = descripcion;
/*  66:    */   }
/*  67:    */   
/*  68:    */   public int getId()
/*  69:    */   {
/*  70:113 */     return this.idProceso;
/*  71:    */   }
/*  72:    */   
/*  73:    */   public int getIdProceso()
/*  74:    */   {
/*  75:122 */     return this.idProceso;
/*  76:    */   }
/*  77:    */   
/*  78:    */   public void setIdProceso(int idProceso)
/*  79:    */   {
/*  80:132 */     this.idProceso = idProceso;
/*  81:    */   }
/*  82:    */   
/*  83:    */   public int getIdOrganizacion()
/*  84:    */   {
/*  85:141 */     return this.idOrganizacion;
/*  86:    */   }
/*  87:    */   
/*  88:    */   public void setIdOrganizacion(int idOrganizacion)
/*  89:    */   {
/*  90:151 */     this.idOrganizacion = idOrganizacion;
/*  91:    */   }
/*  92:    */   
/*  93:    */   public int getIdSucursal()
/*  94:    */   {
/*  95:160 */     return this.idSucursal;
/*  96:    */   }
/*  97:    */   
/*  98:    */   public void setIdSucursal(int idSucursal)
/*  99:    */   {
/* 100:170 */     this.idSucursal = idSucursal;
/* 101:    */   }
/* 102:    */   
/* 103:    */   public String getDescripcion()
/* 104:    */   {
/* 105:179 */     return this.descripcion;
/* 106:    */   }
/* 107:    */   
/* 108:    */   public void setDescripcion(String descripcion)
/* 109:    */   {
/* 110:189 */     this.descripcion = descripcion;
/* 111:    */   }
/* 112:    */   
/* 113:    */   public int getOrden()
/* 114:    */   {
/* 115:198 */     return this.orden;
/* 116:    */   }
/* 117:    */   
/* 118:    */   public void setOrden(int orden)
/* 119:    */   {
/* 120:208 */     this.orden = orden;
/* 121:    */   }
/* 122:    */   
/* 123:    */   public Modulo getModulo()
/* 124:    */   {
/* 125:217 */     return this.modulo;
/* 126:    */   }
/* 127:    */   
/* 128:    */   public void setModulo(Modulo modulo)
/* 129:    */   {
/* 130:227 */     this.modulo = modulo;
/* 131:    */   }
/* 132:    */   
/* 133:    */   public String getViewId()
/* 134:    */   {
/* 135:236 */     return this.viewId;
/* 136:    */   }
/* 137:    */   
/* 138:    */   public void setViewId(String viewId)
/* 139:    */   {
/* 140:246 */     this.viewId = viewId;
/* 141:    */   }
/* 142:    */   
/* 143:    */   public String getViewName()
/* 144:    */   {
/* 145:255 */     return this.viewName;
/* 146:    */   }
/* 147:    */   
/* 148:    */   public void setViewName(String viewName)
/* 149:    */   {
/* 150:265 */     this.viewName = viewName;
/* 151:    */   }
/* 152:    */   
/* 153:    */   public boolean isIndicadorMostrarMenu()
/* 154:    */   {
/* 155:274 */     return this.indicadorMostrarMenu;
/* 156:    */   }
/* 157:    */   
/* 158:    */   public void setIndicadorMostrarMenu(boolean indicadorMostrarMenu)
/* 159:    */   {
/* 160:284 */     this.indicadorMostrarMenu = indicadorMostrarMenu;
/* 161:    */   }
/* 162:    */   
/* 163:    */   public boolean isActivo()
/* 164:    */   {
/* 165:293 */     return this.activo;
/* 166:    */   }
/* 167:    */   
/* 168:    */   public void setActivo(boolean activo)
/* 169:    */   {
/* 170:303 */     this.activo = activo;
/* 171:    */   }
/* 172:    */   
/* 173:    */   public EntidadSistema getSistema()
/* 174:    */   {
/* 175:312 */     return this.sistema;
/* 176:    */   }
/* 177:    */   
/* 178:    */   public void setSistema(EntidadSistema sistema)
/* 179:    */   {
/* 180:322 */     this.sistema = sistema;
/* 181:    */   }
/* 182:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.seguridad.EntidadProceso
 * JD-Core Version:    0.7.0.1
 */