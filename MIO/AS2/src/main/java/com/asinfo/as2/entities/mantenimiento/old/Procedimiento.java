/*   1:    */ package com.asinfo.as2.entities.mantenimiento.old;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.entities.EntidadBase;
/*   4:    */ import com.asinfo.as2.enumeraciones.TipoMantenimiento;
/*   5:    */ import java.util.ArrayList;
/*   6:    */ import java.util.List;
/*   7:    */ import javax.persistence.Column;
/*   8:    */ import javax.persistence.Entity;
/*   9:    */ import javax.persistence.EnumType;
/*  10:    */ import javax.persistence.Enumerated;
/*  11:    */ import javax.persistence.FetchType;
/*  12:    */ import javax.persistence.GeneratedValue;
/*  13:    */ import javax.persistence.GenerationType;
/*  14:    */ import javax.persistence.Id;
/*  15:    */ import javax.persistence.JoinColumn;
/*  16:    */ import javax.persistence.ManyToOne;
/*  17:    */ import javax.persistence.OneToMany;
/*  18:    */ import javax.persistence.Table;
/*  19:    */ import javax.persistence.TableGenerator;
/*  20:    */ import javax.validation.constraints.NotNull;
/*  21:    */ import javax.validation.constraints.Size;
/*  22:    */ 
/*  23:    */ @Entity
/*  24:    */ @Table(name="procedimiento")
/*  25:    */ public class Procedimiento
/*  26:    */   extends EntidadBase
/*  27:    */ {
/*  28:    */   private static final long serialVersionUID = -5926225942068306870L;
/*  29:    */   @Id
/*  30:    */   @TableGenerator(name="procedimiento", initialValue=0, allocationSize=50)
/*  31:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="procedimiento")
/*  32:    */   @Column(name="id_procedimiento")
/*  33:    */   private int idProcedimiento;
/*  34:    */   @Column(name="id_organizacion", nullable=false)
/*  35:    */   private int idOrganizacion;
/*  36:    */   @Column(name="id_sucursal", nullable=false)
/*  37:    */   private int idSucursal;
/*  38:    */   @Column(name="codigo", nullable=false, length=10)
/*  39:    */   @NotNull
/*  40:    */   @Size(min=2, max=10)
/*  41:    */   private String codigo;
/*  42:    */   @Column(name="nombre", nullable=false, length=50)
/*  43:    */   @NotNull
/*  44:    */   @Size(min=2, max=50)
/*  45:    */   private String nombre;
/*  46:    */   @Column(name="descripcion", length=200)
/*  47:    */   @Size(max=200)
/*  48:    */   private String descripcion;
/*  49:    */   @Column(name="activo", nullable=false)
/*  50:    */   private boolean activo;
/*  51:    */   @Column(name="predeterminado", nullable=false)
/*  52:    */   private boolean predeterminado;
/*  53:    */   @Enumerated(EnumType.ORDINAL)
/*  54:    */   @Column(name="tipo_mantenimiento", nullable=false, length=50)
/*  55:    */   @NotNull
/*  56:    */   private TipoMantenimiento tipoMantenimiento;
/*  57:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  58:    */   @JoinColumn(name="id_tipo_servicio_procedimiento", nullable=false)
/*  59:    */   private TipoServicioMantenimiento tipoServicioMantenimiento;
/*  60:    */   @OneToMany(fetch=FetchType.LAZY, mappedBy="procedimiento")
/*  61: 97 */   private List<ProcedimientoActividad> listaProcedimientoActividad = new ArrayList();
/*  62:    */   
/*  63:    */   public Procedimiento() {}
/*  64:    */   
/*  65:    */   public Procedimiento(int idProcedimiento, String nombre)
/*  66:    */   {
/*  67:120 */     this.idProcedimiento = idProcedimiento;
/*  68:121 */     this.nombre = nombre;
/*  69:    */   }
/*  70:    */   
/*  71:    */   public int getIdOrganizacion()
/*  72:    */   {
/*  73:130 */     return this.idOrganizacion;
/*  74:    */   }
/*  75:    */   
/*  76:    */   public int getIdProcedimiento()
/*  77:    */   {
/*  78:139 */     return this.idProcedimiento;
/*  79:    */   }
/*  80:    */   
/*  81:    */   public void setIdProcedimiento(int idProcedimiento)
/*  82:    */   {
/*  83:149 */     this.idProcedimiento = idProcedimiento;
/*  84:    */   }
/*  85:    */   
/*  86:    */   public void setIdOrganizacion(int idOrganizacion)
/*  87:    */   {
/*  88:159 */     this.idOrganizacion = idOrganizacion;
/*  89:    */   }
/*  90:    */   
/*  91:    */   public int getIdSucursal()
/*  92:    */   {
/*  93:168 */     return this.idSucursal;
/*  94:    */   }
/*  95:    */   
/*  96:    */   public void setIdSucursal(int idSucursal)
/*  97:    */   {
/*  98:178 */     this.idSucursal = idSucursal;
/*  99:    */   }
/* 100:    */   
/* 101:    */   public String getCodigo()
/* 102:    */   {
/* 103:187 */     return this.codigo;
/* 104:    */   }
/* 105:    */   
/* 106:    */   public void setCodigo(String codigo)
/* 107:    */   {
/* 108:197 */     this.codigo = codigo;
/* 109:    */   }
/* 110:    */   
/* 111:    */   public String getNombre()
/* 112:    */   {
/* 113:206 */     return this.nombre;
/* 114:    */   }
/* 115:    */   
/* 116:    */   public void setNombre(String nombre)
/* 117:    */   {
/* 118:216 */     this.nombre = nombre;
/* 119:    */   }
/* 120:    */   
/* 121:    */   public String getDescripcion()
/* 122:    */   {
/* 123:225 */     return this.descripcion;
/* 124:    */   }
/* 125:    */   
/* 126:    */   public void setDescripcion(String descripcion)
/* 127:    */   {
/* 128:235 */     this.descripcion = descripcion;
/* 129:    */   }
/* 130:    */   
/* 131:    */   public boolean isActivo()
/* 132:    */   {
/* 133:244 */     return this.activo;
/* 134:    */   }
/* 135:    */   
/* 136:    */   public void setActivo(boolean activo)
/* 137:    */   {
/* 138:254 */     this.activo = activo;
/* 139:    */   }
/* 140:    */   
/* 141:    */   public boolean isPredeterminado()
/* 142:    */   {
/* 143:263 */     return this.predeterminado;
/* 144:    */   }
/* 145:    */   
/* 146:    */   public void setPredeterminado(boolean predeterminado)
/* 147:    */   {
/* 148:273 */     this.predeterminado = predeterminado;
/* 149:    */   }
/* 150:    */   
/* 151:    */   public TipoServicioMantenimiento getTipoServicioMantenimiento()
/* 152:    */   {
/* 153:282 */     return this.tipoServicioMantenimiento;
/* 154:    */   }
/* 155:    */   
/* 156:    */   public void setTipoServicioMantenimiento(TipoServicioMantenimiento tipoServicioMantenimiento)
/* 157:    */   {
/* 158:292 */     this.tipoServicioMantenimiento = tipoServicioMantenimiento;
/* 159:    */   }
/* 160:    */   
/* 161:    */   public int getId()
/* 162:    */   {
/* 163:297 */     return this.idProcedimiento;
/* 164:    */   }
/* 165:    */   
/* 166:    */   public TipoMantenimiento getTipoMantenimiento()
/* 167:    */   {
/* 168:306 */     return this.tipoMantenimiento;
/* 169:    */   }
/* 170:    */   
/* 171:    */   public void setTipoMantenimiento(TipoMantenimiento tipoMantenimiento)
/* 172:    */   {
/* 173:316 */     this.tipoMantenimiento = tipoMantenimiento;
/* 174:    */   }
/* 175:    */   
/* 176:    */   public List<ProcedimientoActividad> getListaProcedimientoActividad()
/* 177:    */   {
/* 178:325 */     return this.listaProcedimientoActividad;
/* 179:    */   }
/* 180:    */   
/* 181:    */   public void setListaProcedimientoActividad(List<ProcedimientoActividad> listaProcedimientoActividad)
/* 182:    */   {
/* 183:335 */     this.listaProcedimientoActividad = listaProcedimientoActividad;
/* 184:    */   }
/* 185:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.mantenimiento.old.Procedimiento
 * JD-Core Version:    0.7.0.1
 */