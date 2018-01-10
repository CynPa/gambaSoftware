/*   1:    */ package com.asinfo.as2.entities;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.enumeraciones.Genero;
/*   4:    */ import javax.persistence.Column;
/*   5:    */ import javax.persistence.Entity;
/*   6:    */ import javax.persistence.EnumType;
/*   7:    */ import javax.persistence.Enumerated;
/*   8:    */ import javax.persistence.FetchType;
/*   9:    */ import javax.persistence.GeneratedValue;
/*  10:    */ import javax.persistence.GenerationType;
/*  11:    */ import javax.persistence.Id;
/*  12:    */ import javax.persistence.JoinColumn;
/*  13:    */ import javax.persistence.ManyToOne;
/*  14:    */ import javax.persistence.OneToOne;
/*  15:    */ import javax.persistence.Table;
/*  16:    */ import javax.persistence.TableGenerator;
/*  17:    */ import javax.validation.constraints.NotNull;
/*  18:    */ import javax.validation.constraints.Size;
/*  19:    */ 
/*  20:    */ @Entity
/*  21:    */ @Table(name="empleado_generico")
/*  22:    */ public class EmpleadoGenerico
/*  23:    */   extends EntidadBase
/*  24:    */ {
/*  25:    */   private static final long serialVersionUID = 7390241955808531503L;
/*  26:    */   @Id
/*  27:    */   @TableGenerator(name="empleado_generico", initialValue=0, allocationSize=50)
/*  28:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="empleado_generico")
/*  29:    */   @Column(name="id_empleado_generico")
/*  30:    */   private int idEmpleadoGenerico;
/*  31:    */   @Column(name="id_organizacion", nullable=false)
/*  32:    */   private int idOrganizacion;
/*  33:    */   @Column(name="id_sucursal", nullable=false)
/*  34:    */   private int idSucursal;
/*  35:    */   @Column(name="codigo", nullable=false, length=20)
/*  36:    */   @Size(min=1, max=20)
/*  37:    */   private String codigo;
/*  38:    */   @Column(name="nombre", nullable=false, length=100)
/*  39:    */   @NotNull
/*  40:    */   @Size(min=1, max=100)
/*  41:    */   private String nombre;
/*  42:    */   @Column(name="genero", nullable=false)
/*  43:    */   @Enumerated(EnumType.ORDINAL)
/*  44:    */   private Genero genero;
/*  45:    */   @Column(name="identificacion", nullable=false, length=20)
/*  46:    */   @NotNull
/*  47:    */   @Size(max=20)
/*  48:    */   private String identificacion;
/*  49:    */   @Column(name="activo", nullable=false)
/*  50:    */   private boolean activo;
/*  51:    */   @Column(name="predeterminado", nullable=false)
/*  52:    */   private boolean predeterminado;
/*  53:    */   @OneToOne(fetch=FetchType.LAZY)
/*  54:    */   @JoinColumn(name="id_empresa", nullable=true)
/*  55:    */   private Empresa empresa;
/*  56:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  57:    */   @JoinColumn(name="id_especialidad", nullable=false)
/*  58:    */   private Especialidad especialidad;
/*  59:    */   
/*  60:    */   public int getIdEmpleadoGenerico()
/*  61:    */   {
/*  62:113 */     return this.idEmpleadoGenerico;
/*  63:    */   }
/*  64:    */   
/*  65:    */   public void setIdEmpleadoGenerico(int idEmpleadoGenerico)
/*  66:    */   {
/*  67:123 */     this.idEmpleadoGenerico = idEmpleadoGenerico;
/*  68:    */   }
/*  69:    */   
/*  70:    */   public int getIdOrganizacion()
/*  71:    */   {
/*  72:132 */     return this.idOrganizacion;
/*  73:    */   }
/*  74:    */   
/*  75:    */   public void setIdOrganizacion(int idOrganizacion)
/*  76:    */   {
/*  77:142 */     this.idOrganizacion = idOrganizacion;
/*  78:    */   }
/*  79:    */   
/*  80:    */   public int getIdSucursal()
/*  81:    */   {
/*  82:151 */     return this.idSucursal;
/*  83:    */   }
/*  84:    */   
/*  85:    */   public void setIdSucursal(int idSucursal)
/*  86:    */   {
/*  87:161 */     this.idSucursal = idSucursal;
/*  88:    */   }
/*  89:    */   
/*  90:    */   public String getCodigo()
/*  91:    */   {
/*  92:170 */     return this.codigo;
/*  93:    */   }
/*  94:    */   
/*  95:    */   public void setCodigo(String codigo)
/*  96:    */   {
/*  97:180 */     this.codigo = codigo;
/*  98:    */   }
/*  99:    */   
/* 100:    */   public String getNombre()
/* 101:    */   {
/* 102:189 */     return this.nombre;
/* 103:    */   }
/* 104:    */   
/* 105:    */   public void setNombre(String nombre)
/* 106:    */   {
/* 107:199 */     this.nombre = nombre;
/* 108:    */   }
/* 109:    */   
/* 110:    */   public Genero getGenero()
/* 111:    */   {
/* 112:208 */     return this.genero;
/* 113:    */   }
/* 114:    */   
/* 115:    */   public void setGenero(Genero genero)
/* 116:    */   {
/* 117:218 */     this.genero = genero;
/* 118:    */   }
/* 119:    */   
/* 120:    */   public String getIdentificacion()
/* 121:    */   {
/* 122:227 */     return this.identificacion;
/* 123:    */   }
/* 124:    */   
/* 125:    */   public void setIdentificacion(String identificacion)
/* 126:    */   {
/* 127:237 */     this.identificacion = identificacion;
/* 128:    */   }
/* 129:    */   
/* 130:    */   public Empresa getEmpresa()
/* 131:    */   {
/* 132:246 */     return this.empresa;
/* 133:    */   }
/* 134:    */   
/* 135:    */   public void setEmpresa(Empresa empresa)
/* 136:    */   {
/* 137:256 */     this.empresa = empresa;
/* 138:    */   }
/* 139:    */   
/* 140:    */   public boolean isActivo()
/* 141:    */   {
/* 142:265 */     return this.activo;
/* 143:    */   }
/* 144:    */   
/* 145:    */   public void setActivo(boolean activo)
/* 146:    */   {
/* 147:275 */     this.activo = activo;
/* 148:    */   }
/* 149:    */   
/* 150:    */   public boolean isPredeterminado()
/* 151:    */   {
/* 152:284 */     return this.predeterminado;
/* 153:    */   }
/* 154:    */   
/* 155:    */   public void setPredeterminado(boolean predeterminado)
/* 156:    */   {
/* 157:294 */     this.predeterminado = predeterminado;
/* 158:    */   }
/* 159:    */   
/* 160:    */   public int getId()
/* 161:    */   {
/* 162:304 */     return this.idEmpleadoGenerico;
/* 163:    */   }
/* 164:    */   
/* 165:    */   public Especialidad getEspecialidad()
/* 166:    */   {
/* 167:313 */     return this.especialidad;
/* 168:    */   }
/* 169:    */   
/* 170:    */   public void setEspecialidad(Especialidad especialidad)
/* 171:    */   {
/* 172:323 */     this.especialidad = especialidad;
/* 173:    */   }
/* 174:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.EmpleadoGenerico
 * JD-Core Version:    0.7.0.1
 */