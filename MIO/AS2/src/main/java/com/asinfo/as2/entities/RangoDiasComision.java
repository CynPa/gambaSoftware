/*   1:    */ package com.asinfo.as2.entities;
/*   2:    */ 
/*   3:    */ import javax.persistence.Column;
/*   4:    */ import javax.persistence.Entity;
/*   5:    */ import javax.persistence.GeneratedValue;
/*   6:    */ import javax.persistence.GenerationType;
/*   7:    */ import javax.persistence.Id;
/*   8:    */ import javax.persistence.Table;
/*   9:    */ import javax.persistence.TableGenerator;
/*  10:    */ import javax.validation.constraints.Min;
/*  11:    */ import javax.validation.constraints.NotNull;
/*  12:    */ import javax.validation.constraints.Size;
/*  13:    */ 
/*  14:    */ @Entity
/*  15:    */ @Table(name="rango_dias_comision", uniqueConstraints={@javax.persistence.UniqueConstraint(columnNames={"id_organizacion", "codigo"})})
/*  16:    */ public class RangoDiasComision
/*  17:    */   extends EntidadBase
/*  18:    */ {
/*  19:    */   private static final long serialVersionUID = 1L;
/*  20:    */   @Id
/*  21:    */   @TableGenerator(name="rango_dias_comision", initialValue=0, allocationSize=50)
/*  22:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="rango_dias_comision")
/*  23:    */   @Column(name="id_rango_dias_comision")
/*  24:    */   private int idRangoDiasComision;
/*  25:    */   @Column(name="id_organizacion", nullable=false)
/*  26:    */   private int idOrganizacion;
/*  27:    */   @Column(name="id_sucursal", nullable=false)
/*  28:    */   private int idSucursal;
/*  29:    */   @Column(name="codigo", nullable=false, length=10)
/*  30:    */   @Size(min=2, max=10)
/*  31:    */   private String codigo;
/*  32:    */   @Column(name="nombre", nullable=false, length=50)
/*  33:    */   @Size(min=2, max=50)
/*  34:    */   private String nombre;
/*  35:    */   @Column(name="descripcion", nullable=true, length=200)
/*  36:    */   @Size(max=200)
/*  37:    */   private String descripcion;
/*  38:    */   @Column(name="activo", nullable=false)
/*  39:    */   private boolean activo;
/*  40:    */   @Column(name="predeterminado", nullable=false)
/*  41:    */   private boolean predeterminado;
/*  42:    */   @Column(name="dia_inicial", nullable=false)
/*  43:    */   @NotNull
/*  44:    */   @Min(0L)
/*  45:    */   private int diaInicial;
/*  46:    */   @Column(name="dia_final", nullable=false)
/*  47:    */   @NotNull
/*  48:    */   @Min(0L)
/*  49:    */   private int diaFinal;
/*  50:    */   
/*  51:    */   public int getId()
/*  52:    */   {
/*  53:101 */     return this.idRangoDiasComision;
/*  54:    */   }
/*  55:    */   
/*  56:    */   public int getIdRangoDiasComision()
/*  57:    */   {
/*  58:105 */     return this.idRangoDiasComision;
/*  59:    */   }
/*  60:    */   
/*  61:    */   public void setIdRangoDiasComision(int idRangoDiasComision)
/*  62:    */   {
/*  63:109 */     this.idRangoDiasComision = idRangoDiasComision;
/*  64:    */   }
/*  65:    */   
/*  66:    */   public int getIdOrganizacion()
/*  67:    */   {
/*  68:113 */     return this.idOrganizacion;
/*  69:    */   }
/*  70:    */   
/*  71:    */   public void setIdOrganizacion(int idOrganizacion)
/*  72:    */   {
/*  73:117 */     this.idOrganizacion = idOrganizacion;
/*  74:    */   }
/*  75:    */   
/*  76:    */   public int getIdSucursal()
/*  77:    */   {
/*  78:121 */     return this.idSucursal;
/*  79:    */   }
/*  80:    */   
/*  81:    */   public void setIdSucursal(int idSucursal)
/*  82:    */   {
/*  83:125 */     this.idSucursal = idSucursal;
/*  84:    */   }
/*  85:    */   
/*  86:    */   public String getCodigo()
/*  87:    */   {
/*  88:129 */     return this.codigo;
/*  89:    */   }
/*  90:    */   
/*  91:    */   public void setCodigo(String codigo)
/*  92:    */   {
/*  93:133 */     this.codigo = codigo;
/*  94:    */   }
/*  95:    */   
/*  96:    */   public String getNombre()
/*  97:    */   {
/*  98:137 */     return this.nombre;
/*  99:    */   }
/* 100:    */   
/* 101:    */   public void setNombre(String nombre)
/* 102:    */   {
/* 103:141 */     this.nombre = nombre;
/* 104:    */   }
/* 105:    */   
/* 106:    */   public String getDescripcion()
/* 107:    */   {
/* 108:145 */     return this.descripcion;
/* 109:    */   }
/* 110:    */   
/* 111:    */   public void setDescripcion(String descripcion)
/* 112:    */   {
/* 113:149 */     this.descripcion = descripcion;
/* 114:    */   }
/* 115:    */   
/* 116:    */   public boolean isActivo()
/* 117:    */   {
/* 118:153 */     return this.activo;
/* 119:    */   }
/* 120:    */   
/* 121:    */   public void setActivo(boolean activo)
/* 122:    */   {
/* 123:157 */     this.activo = activo;
/* 124:    */   }
/* 125:    */   
/* 126:    */   public int getDiaInicial()
/* 127:    */   {
/* 128:161 */     return this.diaInicial;
/* 129:    */   }
/* 130:    */   
/* 131:    */   public void setDiaInicial(int diaInicial)
/* 132:    */   {
/* 133:165 */     this.diaInicial = diaInicial;
/* 134:    */   }
/* 135:    */   
/* 136:    */   public int getDiaFinal()
/* 137:    */   {
/* 138:169 */     return this.diaFinal;
/* 139:    */   }
/* 140:    */   
/* 141:    */   public void setDiaFinal(int diaFinal)
/* 142:    */   {
/* 143:173 */     this.diaFinal = diaFinal;
/* 144:    */   }
/* 145:    */   
/* 146:    */   public boolean isPredeterminado()
/* 147:    */   {
/* 148:177 */     return this.predeterminado;
/* 149:    */   }
/* 150:    */   
/* 151:    */   public void setPredeterminado(boolean predeterminado)
/* 152:    */   {
/* 153:181 */     this.predeterminado = predeterminado;
/* 154:    */   }
/* 155:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.RangoDiasComision
 * JD-Core Version:    0.7.0.1
 */