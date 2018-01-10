/*   1:    */ package com.asinfo.as2.entities;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.enumeraciones.TipoCentroTrabajo;
/*   4:    */ import java.io.Serializable;
/*   5:    */ import javax.persistence.Column;
/*   6:    */ import javax.persistence.Entity;
/*   7:    */ import javax.persistence.EnumType;
/*   8:    */ import javax.persistence.Enumerated;
/*   9:    */ import javax.persistence.FetchType;
/*  10:    */ import javax.persistence.GeneratedValue;
/*  11:    */ import javax.persistence.GenerationType;
/*  12:    */ import javax.persistence.Id;
/*  13:    */ import javax.persistence.JoinColumn;
/*  14:    */ import javax.persistence.ManyToOne;
/*  15:    */ import javax.persistence.Table;
/*  16:    */ import javax.persistence.TableGenerator;
/*  17:    */ import javax.validation.constraints.NotNull;
/*  18:    */ import javax.validation.constraints.Size;
/*  19:    */ 
/*  20:    */ @Entity
/*  21:    */ @Table(name="centro_trabajo", uniqueConstraints={@javax.persistence.UniqueConstraint(columnNames={"id_organizacion", "codigo"})})
/*  22:    */ public class CentroTrabajo
/*  23:    */   extends EntidadBase
/*  24:    */   implements Serializable
/*  25:    */ {
/*  26:    */   private static final long serialVersionUID = 2562293891738034098L;
/*  27:    */   @Id
/*  28:    */   @TableGenerator(name="centro_trabajo", initialValue=0, allocationSize=50)
/*  29:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="centro_trabajo")
/*  30:    */   @Column(name="id_centro_trabajo")
/*  31:    */   private int idCentroTrabajo;
/*  32:    */   @Column(name="id_organizacion")
/*  33:    */   private int idOrganizacion;
/*  34:    */   @Column(name="id_sucursal")
/*  35:    */   private int idSucursal;
/*  36:    */   @Column(name="codigo", nullable=false, length=20)
/*  37:    */   @NotNull
/*  38:    */   @Size(min=1, max=20)
/*  39:    */   private String codigo;
/*  40:    */   @Column(name="nombre", nullable=false, length=100)
/*  41:    */   @NotNull
/*  42:    */   @Size(min=2, max=100)
/*  43:    */   private String nombre;
/*  44:    */   @Column(name="descripcion", length=200, nullable=true)
/*  45:    */   @Size(max=200)
/*  46:    */   private String descripcion;
/*  47:    */   @Column(name="predeterminado", nullable=false)
/*  48:    */   private boolean predeterminado;
/*  49:    */   @Column(name="activo", nullable=false)
/*  50:    */   private boolean activo;
/*  51:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  52:    */   @JoinColumn(name="id_departamento", nullable=true)
/*  53:    */   private Departamento departamento;
/*  54:    */   @Enumerated(EnumType.ORDINAL)
/*  55:    */   @Column(name="tipo_centro_trabajo", nullable=false)
/*  56:    */   @NotNull
/*  57:    */   private TipoCentroTrabajo tipoCentroTrabajo;
/*  58:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  59:    */   @JoinColumn(name="id_empleado_generico", nullable=false)
/*  60:    */   @NotNull
/*  61:    */   private EmpleadoGenerico empleadoGenerico;
/*  62:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  63:    */   @JoinColumn(name="id_bodega_trabajo", nullable=false)
/*  64:    */   @NotNull
/*  65:    */   private Bodega bodegaTrabajo;
/*  66:    */   
/*  67:    */   public CentroTrabajo() {}
/*  68:    */   
/*  69:    */   public CentroTrabajo(int idCentroTrabajo, String nombre)
/*  70:    */   {
/*  71:112 */     this.idCentroTrabajo = idCentroTrabajo;
/*  72:113 */     this.nombre = nombre;
/*  73:    */   }
/*  74:    */   
/*  75:    */   public int getIdCentroTrabajo()
/*  76:    */   {
/*  77:123 */     return this.idCentroTrabajo;
/*  78:    */   }
/*  79:    */   
/*  80:    */   public void setIdCentroTrabajo(int idCentroTrabajo)
/*  81:    */   {
/*  82:133 */     this.idCentroTrabajo = idCentroTrabajo;
/*  83:    */   }
/*  84:    */   
/*  85:    */   public int getIdOrganizacion()
/*  86:    */   {
/*  87:142 */     return this.idOrganizacion;
/*  88:    */   }
/*  89:    */   
/*  90:    */   public void setIdOrganizacion(int idOrganizacion)
/*  91:    */   {
/*  92:152 */     this.idOrganizacion = idOrganizacion;
/*  93:    */   }
/*  94:    */   
/*  95:    */   public int getIdSucursal()
/*  96:    */   {
/*  97:161 */     return this.idSucursal;
/*  98:    */   }
/*  99:    */   
/* 100:    */   public void setIdSucursal(int idSucursal)
/* 101:    */   {
/* 102:171 */     this.idSucursal = idSucursal;
/* 103:    */   }
/* 104:    */   
/* 105:    */   public String getCodigo()
/* 106:    */   {
/* 107:180 */     return this.codigo;
/* 108:    */   }
/* 109:    */   
/* 110:    */   public void setCodigo(String codigo)
/* 111:    */   {
/* 112:190 */     this.codigo = codigo;
/* 113:    */   }
/* 114:    */   
/* 115:    */   public String getNombre()
/* 116:    */   {
/* 117:199 */     return this.nombre;
/* 118:    */   }
/* 119:    */   
/* 120:    */   public void setNombre(String nombre)
/* 121:    */   {
/* 122:209 */     this.nombre = nombre;
/* 123:    */   }
/* 124:    */   
/* 125:    */   public String getDescripcion()
/* 126:    */   {
/* 127:218 */     return this.descripcion;
/* 128:    */   }
/* 129:    */   
/* 130:    */   public void setDescripcion(String descripcion)
/* 131:    */   {
/* 132:228 */     this.descripcion = descripcion;
/* 133:    */   }
/* 134:    */   
/* 135:    */   public boolean isPredeterminado()
/* 136:    */   {
/* 137:237 */     return this.predeterminado;
/* 138:    */   }
/* 139:    */   
/* 140:    */   public void setPredeterminado(boolean predeterminado)
/* 141:    */   {
/* 142:247 */     this.predeterminado = predeterminado;
/* 143:    */   }
/* 144:    */   
/* 145:    */   public boolean isActivo()
/* 146:    */   {
/* 147:256 */     return this.activo;
/* 148:    */   }
/* 149:    */   
/* 150:    */   public void setActivo(boolean activo)
/* 151:    */   {
/* 152:266 */     this.activo = activo;
/* 153:    */   }
/* 154:    */   
/* 155:    */   public Departamento getDepartamento()
/* 156:    */   {
/* 157:275 */     return this.departamento;
/* 158:    */   }
/* 159:    */   
/* 160:    */   public void setDepartamento(Departamento departamento)
/* 161:    */   {
/* 162:285 */     this.departamento = departamento;
/* 163:    */   }
/* 164:    */   
/* 165:    */   public TipoCentroTrabajo getTipoCentroTrabajo()
/* 166:    */   {
/* 167:294 */     return this.tipoCentroTrabajo;
/* 168:    */   }
/* 169:    */   
/* 170:    */   public void setTipoCentroTrabajo(TipoCentroTrabajo tipoCentroTrabajo)
/* 171:    */   {
/* 172:304 */     this.tipoCentroTrabajo = tipoCentroTrabajo;
/* 173:    */   }
/* 174:    */   
/* 175:    */   public EmpleadoGenerico getEmpleadoGenerico()
/* 176:    */   {
/* 177:313 */     return this.empleadoGenerico;
/* 178:    */   }
/* 179:    */   
/* 180:    */   public void setEmpleadoGenerico(EmpleadoGenerico empleadoGenerico)
/* 181:    */   {
/* 182:323 */     this.empleadoGenerico = empleadoGenerico;
/* 183:    */   }
/* 184:    */   
/* 185:    */   public int getId()
/* 186:    */   {
/* 187:333 */     return this.idCentroTrabajo;
/* 188:    */   }
/* 189:    */   
/* 190:    */   public Bodega getBodegaTrabajo()
/* 191:    */   {
/* 192:342 */     return this.bodegaTrabajo;
/* 193:    */   }
/* 194:    */   
/* 195:    */   public void setBodegaTrabajo(Bodega bodegaTrabajo)
/* 196:    */   {
/* 197:352 */     this.bodegaTrabajo = bodegaTrabajo;
/* 198:    */   }
/* 199:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.CentroTrabajo
 * JD-Core Version:    0.7.0.1
 */