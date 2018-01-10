/*   1:    */ package com.asinfo.as2.rs.datosbase.dto;
/*   2:    */ 
/*   3:    */ import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
/*   4:    */ import java.io.Serializable;
/*   5:    */ 
/*   6:    */ @JsonIgnoreProperties(ignoreUnknown=true)
/*   7:    */ public class UbicacionResponseDto
/*   8:    */   implements Serializable
/*   9:    */ {
/*  10:    */   private int idOrganizacion;
/*  11:    */   private int idSucursal;
/*  12:    */   private Integer idUbicacion;
/*  13: 17 */   private String direccion1 = "";
/*  14: 19 */   private String direccion2 = "";
/*  15: 21 */   private String direccion3 = "";
/*  16: 23 */   private String direccion4 = "";
/*  17: 25 */   private String direccion5 = "";
/*  18: 27 */   private int hashCode = 0;
/*  19:    */   
/*  20:    */   public int getHashCode()
/*  21:    */   {
/*  22: 30 */     this.hashCode = hashCode();
/*  23: 31 */     return this.hashCode;
/*  24:    */   }
/*  25:    */   
/*  26:    */   public int hashCode()
/*  27:    */   {
/*  28: 36 */     int hash = 1;
/*  29: 37 */     hash += hash * 41 + (this.idOrganizacion + "").hashCode();
/*  30: 38 */     hash += hash * 36 + (this.idSucursal + "").hashCode();
/*  31: 39 */     hash += hash * 11 + (this.idUbicacion + "").hashCode();
/*  32: 40 */     hash += hash * 15 + (this.direccion1 + "").hashCode();
/*  33: 41 */     hash += hash * 13 + (this.direccion2 + "").hashCode();
/*  34: 42 */     hash += hash * 12 + (this.direccion3 + "").hashCode();
/*  35: 43 */     hash += hash * 10 + (this.direccion4 + "").hashCode();
/*  36: 44 */     hash += hash * 7 + (this.direccion5 + "").hashCode();
/*  37:    */     
/*  38: 46 */     return hash;
/*  39:    */   }
/*  40:    */   
/*  41:    */   public int getIdOrganizacion()
/*  42:    */   {
/*  43: 50 */     return this.idOrganizacion;
/*  44:    */   }
/*  45:    */   
/*  46:    */   public void setIdOrganizacion(int idOrganizacion)
/*  47:    */   {
/*  48: 54 */     this.idOrganizacion = idOrganizacion;
/*  49:    */   }
/*  50:    */   
/*  51:    */   public int getIdSucursal()
/*  52:    */   {
/*  53: 58 */     return this.idSucursal;
/*  54:    */   }
/*  55:    */   
/*  56:    */   public void setIdSucursal(int idSucursal)
/*  57:    */   {
/*  58: 62 */     this.idSucursal = idSucursal;
/*  59:    */   }
/*  60:    */   
/*  61:    */   public Integer getIdUbicacion()
/*  62:    */   {
/*  63: 66 */     return this.idUbicacion;
/*  64:    */   }
/*  65:    */   
/*  66:    */   public void setIdUbicacion(Integer idUbicacion)
/*  67:    */   {
/*  68: 70 */     this.idUbicacion = idUbicacion;
/*  69:    */   }
/*  70:    */   
/*  71:    */   public void setHashCode(int hashCode)
/*  72:    */   {
/*  73: 74 */     this.hashCode = hashCode;
/*  74:    */   }
/*  75:    */   
/*  76:    */   public String getDireccion1()
/*  77:    */   {
/*  78: 78 */     return this.direccion1;
/*  79:    */   }
/*  80:    */   
/*  81:    */   public void setDireccion1(String direccion1)
/*  82:    */   {
/*  83: 82 */     this.direccion1 = direccion1;
/*  84:    */   }
/*  85:    */   
/*  86:    */   public String getDireccion2()
/*  87:    */   {
/*  88: 86 */     return this.direccion2;
/*  89:    */   }
/*  90:    */   
/*  91:    */   public void setDireccion2(String direccion2)
/*  92:    */   {
/*  93: 90 */     this.direccion2 = direccion2;
/*  94:    */   }
/*  95:    */   
/*  96:    */   public String getDireccion3()
/*  97:    */   {
/*  98: 94 */     return this.direccion3;
/*  99:    */   }
/* 100:    */   
/* 101:    */   public void setDireccion3(String direccion3)
/* 102:    */   {
/* 103: 98 */     this.direccion3 = direccion3;
/* 104:    */   }
/* 105:    */   
/* 106:    */   public String getDireccion4()
/* 107:    */   {
/* 108:102 */     return this.direccion4;
/* 109:    */   }
/* 110:    */   
/* 111:    */   public void setDireccion4(String direccion4)
/* 112:    */   {
/* 113:106 */     this.direccion4 = direccion4;
/* 114:    */   }
/* 115:    */   
/* 116:    */   public String getDireccion5()
/* 117:    */   {
/* 118:110 */     return this.direccion5;
/* 119:    */   }
/* 120:    */   
/* 121:    */   public void setDireccion5(String direccion5)
/* 122:    */   {
/* 123:114 */     this.direccion5 = direccion5;
/* 124:    */   }
/* 125:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.rs.datosbase.dto.UbicacionResponseDto
 * JD-Core Version:    0.7.0.1
 */